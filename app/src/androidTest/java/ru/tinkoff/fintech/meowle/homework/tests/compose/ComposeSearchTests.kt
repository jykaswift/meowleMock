package ru.tinkoff.fintech.meowle.homework.tests.compose

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.kaspersky.components.composesupport.config.withComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import ru.tinkoff.fintech.meowle.homework.composeScreens.ComposeCatProfileScreen
import ru.tinkoff.fintech.meowle.homework.composeScreens.ComposeSearchScreen
import ru.tinkoff.fintech.meowle.homework.dto.CatResponseDTO
import ru.tinkoff.fintech.meowle.homework.rules.CleanPreferencesRule
import ru.tinkoff.fintech.meowle.homework.rules.ComposePreferencesRule
import ru.tinkoff.fintech.meowle.homework.rules.MockServerPreferencesRule
import ru.tinkoff.fintech.meowle.homework.utils.MockData
import ru.tinkoff.fintech.meowle.presentation.MainActivity

class ComposeSearchTests: TestCase(
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
    fun searchCatTest() = run {

        val searchScreen = ComposeSearchScreen(composeTestRule)

        val searchResults = mockData.searchResults
        val firstCat = searchResults.cats.first()
        stubFor(post(urlPathMatching(".*/search"))
            .willReturn(WireMock.ok(searchResults.toJson()))
        )

        searchScreen.searchBar.findCat(firstCat.name)
        searchScreen.checkCatNameAt(0, firstCat.name)
    }

    @Test
    fun compareSearchResultTest() {
        val searchResults = mockData.searchResults
        val firstCat = searchResults.cats.first()
        val catResponse = CatResponseDTO(firstCat)
        val catPhotos = mockData.catEmptyPhotosList

        val searchScreen = ComposeSearchScreen(composeTestRule)
        val catProfileScreen = ComposeCatProfileScreen(composeTestRule)

        stubFor(post(urlPathMatching(".*/search"))
            .willReturn(WireMock.ok(searchResults.toJson()))
        )

        stubFor(get(urlPathMatching(".*/get-by-id.*"))
            .willReturn(WireMock.ok(catResponse.toJson()))
        )

        stubFor(get(urlPathMatching(".*/photos"))
            .willReturn(WireMock.ok(catPhotos.toJson()))
        )

        searchScreen.searchBar.findCat(firstCat.name)
        searchScreen.clickCatAt(0)
        catProfileScreen.compareCatNameWith(firstCat.name)
        catProfileScreen.compareCatDescriptionWith(firstCat.description)
        catProfileScreen.compareCatLikesCountWith(firstCat.likes)
    }
}