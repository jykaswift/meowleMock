package ru.tinkoff.fintech.meowle.homework.tests.kaspresso

import androidx.test.ext.junit.rules.activityScenarioRule
import com.github.tomakehurst.wiremock.client.WireMock.equalToJson
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching
import com.github.tomakehurst.wiremock.client.WireMock.verify
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.params.FlakySafetyParams
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import ru.tinkoff.fintech.meowle.homework.dto.CatResponseDTO
import ru.tinkoff.fintech.meowle.homework.dto.ChangeRatingDTO
import ru.tinkoff.fintech.meowle.homework.kaspressoScreens.KaspressoCatProfileScreen
import ru.tinkoff.fintech.meowle.homework.kaspressoScreens.KaspressoRatingScreen
import ru.tinkoff.fintech.meowle.homework.rules.CleanPreferencesRule
import ru.tinkoff.fintech.meowle.homework.rules.MockServerPreferencesRule
import ru.tinkoff.fintech.meowle.homework.utils.MockData
import ru.tinkoff.fintech.meowle.homework.utils.NavigationButton
import ru.tinkoff.fintech.meowle.presentation.MainActivity

class KaspressoRatingTest: TestCase(
    kaspressoBuilder = Kaspresso.Builder.simple(
        customize = {
            flakySafetyParams = FlakySafetyParams.custom(timeoutMs = 10_000, intervalMs = 250)
        }
    )
) {
    private val mockData = MockData()

    private val cleanPreferencesRule = CleanPreferencesRule()
    private val mockServerPreferencesRule = MockServerPreferencesRule()
    private val activityScenarioRule = activityScenarioRule<MainActivity>()
    private val mockRule = WireMockRule(5000)

    @get:Rule
    val ruleChain: RuleChain = RuleChain
        .outerRule(mockRule)
        .around(mockServerPreferencesRule)
        .around(activityScenarioRule)
        .around(cleanPreferencesRule)

    @Test
    fun likeMostDislikeCatTest() = run {
        val ratingList = mockData.ratingList
        val firstCat = ratingList.cats.first()
        val catResponse = CatResponseDTO(firstCat)
        val catPhotos = mockData.catEmptyPhotosList

        val ratingScreen = KaspressoRatingScreen()
        val profileScreen = KaspressoCatProfileScreen()
        val changeRatingDTO = ChangeRatingDTO(false, true)

        stubFor(get(urlPathMatching(".*/rating"))
            .willReturn(ok(ratingList.toJson()))
        )

        stubFor(get(urlPathMatching(".*/get-by-id.*"))
            .willReturn(ok(catResponse.toJson()))
        )

        stubFor(get(urlPathMatching(".*/photos"))
            .willReturn(ok(catPhotos.toJson()))
        )

        stubFor(post(urlPathMatching(".*/${firstCat.id}/likes"))
            .willReturn(ok(firstCat.toJson()))
        )

        ratingScreen.bottomNavigation.clickButton(NavigationButton.RATING)
        ratingScreen.clickDislikesButton()
        ratingScreen.compareCatName(firstCat.name, 0)
        ratingScreen.clickCatAtPosition(0)

        profileScreen.clickLikeButton()
        verify(postRequestedFor(urlPathMatching(".*/${firstCat.id}/likes"))
            .withRequestBody(equalToJson(changeRatingDTO.toJson()))
        )
    }

}