package ru.tinkoff.fintech.meowle.homework.tests.compose
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.github.tomakehurst.wiremock.client.WireMock.equalToJson
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.ok
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
import ru.tinkoff.fintech.meowle.homework.dto.ChangeDescriptionRequestDTO
import ru.tinkoff.fintech.meowle.homework.rules.CleanPreferencesRule
import ru.tinkoff.fintech.meowle.homework.rules.ComposePreferencesRule
import ru.tinkoff.fintech.meowle.homework.rules.MockServerPreferencesRule
import ru.tinkoff.fintech.meowle.homework.utils.MockData
import ru.tinkoff.fintech.meowle.homework.utils.NavigationButton
import ru.tinkoff.fintech.meowle.presentation.MainActivity

class ComposeCatProfileTests: TestCase(
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
        val fifthCatIndex = 4
        val ratingList = mockData.ratingList
        val fifthCat = ratingList.cats[fifthCatIndex]
        val catResponse = CatResponseDTO(fifthCat)
        val catPhotos = mockData.catEmptyPhotosList

        val ratingScreen = ComposeRatingScreen(composeTestRule)
        val profileScreen = ComposeCatProfileScreen(composeTestRule)
        val newDescription = "Описание"

        stubFor(get(urlPathMatching(".*/rating"))
            .willReturn(ok(ratingList.toJson()))
        )

        stubFor(get(urlPathMatching(".*/get-by-id.*"))
            .willReturn(ok(catResponse.toJson()))
        )

        stubFor(get(urlPathMatching(".*/photos"))
            .willReturn(ok(catPhotos.toJson()))
        )

        stubFor(post(urlPathMatching(".*/save-description"))
            .willReturn(ok(fifthCat.copy(description = newDescription).toJson()))
        )

        ratingScreen.bottomNavigation.clickBottomNavigationButton(NavigationButton.RATING)
        ratingScreen.clickCatAt(fifthCatIndex)

        profileScreen.clickEditDescriptionButton()
        profileScreen.changeDescriptionWith(newDescription)
        profileScreen.clickSaveDescriptionButton()

        val changeDescriptionRequest = ChangeDescriptionRequestDTO(newDescription, fifthCat.id)
        verify(postRequestedFor(urlPathMatching(".*/save-description"))
            .withRequestBody(equalToJson(changeDescriptionRequest.toJson()))
        )

        profileScreen.compareCatDescriptionWith(newDescription)
    }

}