package ru.tinkoff.fintech.meowle.homework.tests.compose

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.equalToJson
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching
import com.github.tomakehurst.wiremock.client.WireMock.verify
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.kaspersky.components.composesupport.config.withComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import ru.tinkoff.fintech.meowle.homework.composeScreens.ComposeCatProfileScreen
import ru.tinkoff.fintech.meowle.homework.composeScreens.ComposeRatingScreen
import ru.tinkoff.fintech.meowle.homework.dto.CatResponseDTO
import ru.tinkoff.fintech.meowle.homework.dto.ChangeRatingDTO
import ru.tinkoff.fintech.meowle.homework.rules.CleanPreferencesRule
import ru.tinkoff.fintech.meowle.homework.rules.ComposePreferencesRule
import ru.tinkoff.fintech.meowle.homework.rules.MockServerPreferencesRule
import ru.tinkoff.fintech.meowle.homework.utils.MockData
import ru.tinkoff.fintech.meowle.homework.utils.NavigationButton
import ru.tinkoff.fintech.meowle.presentation.MainActivity

class ComposeRatingTests: TestCase(
    kaspressoBuilder = Kaspresso.Builder.withComposeSupport()
) {
    private val cleanPreferencesRule = CleanPreferencesRule()
    private val mockServerPreferencesRule = MockServerPreferencesRule()
    private val composePreferencesRule = ComposePreferencesRule()
    private val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val mockRule = WireMockRule(5000)

    @get:Rule
    val ruleChain: RuleChain = RuleChain
        .outerRule(mockRule)
        .around(mockServerPreferencesRule)
        .around(composePreferencesRule)
        .around(composeTestRule)
        .around(cleanPreferencesRule)

    private val mockData = MockData()

    @Test
    fun likeMostDislikeCatTest() = run {
        val ratingList = mockData.ratingList
        val firstCat = ratingList.cats.first()
        val catResponse = CatResponseDTO(firstCat)
        val catPhotos = mockData.catEmptyPhotosList

        val ratingScreen = ComposeRatingScreen(composeTestRule)
        val profileScreen = ComposeCatProfileScreen(composeTestRule)
        val changeRatingDTO = ChangeRatingDTO(false, true)

        stubFor(get(urlPathMatching(".*/rating"))
            .willReturn(WireMock.ok(ratingList.toJson()))
        )

        stubFor(get(urlPathMatching(".*/get-by-id.*"))
            .willReturn(WireMock.ok(catResponse.toJson()))
        )

        stubFor(get(urlPathMatching(".*/photos"))
            .willReturn(WireMock.ok(catPhotos.toJson()))
        )

        stubFor(post(urlPathMatching(".*/${firstCat.id}/likes"))
            .willReturn(WireMock.ok(firstCat.toJson()))
        )

        ratingScreen.bottomNavigation.clickBottomNavigationButton(NavigationButton.RATING)
        ratingScreen.clickDislikesButton()
        ratingScreen.compareCatNameAt(0, firstCat.name)
        ratingScreen.clickCatAt(0)

        profileScreen.clickLikeButton()
        verify(postRequestedFor(urlPathMatching(".*/${firstCat.id}/likes"))
                .withRequestBody(equalToJson(changeRatingDTO.toJson()))
        )


    }

}