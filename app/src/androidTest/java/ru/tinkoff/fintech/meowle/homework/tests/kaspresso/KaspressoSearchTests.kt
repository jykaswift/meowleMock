package ru.tinkoff.fintech.meowle.homework.tests.kaspresso

import androidx.test.ext.junit.rules.activityScenarioRule
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.params.FlakySafetyParams
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import ru.tinkoff.fintech.meowle.homework.dto.CatResponseDTO
import ru.tinkoff.fintech.meowle.homework.kaspressoScreens.KaspressoCatProfileScreen
import ru.tinkoff.fintech.meowle.homework.kaspressoScreens.KaspressoSearchScreen
import ru.tinkoff.fintech.meowle.homework.rules.CleanPreferencesRule
import ru.tinkoff.fintech.meowle.homework.rules.MockServerPreferencesRule
import ru.tinkoff.fintech.meowle.homework.utils.MockData
import ru.tinkoff.fintech.meowle.presentation.MainActivity

class KaspressoSearchTests: TestCase(
    kaspressoBuilder = Kaspresso.Builder.simple(
        customize = {
            flakySafetyParams = FlakySafetyParams.custom(timeoutMs = 10_000, intervalMs = 250)
        }
    )
) {
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

    private val mockData = MockData()

    @Test
    fun searchCatTest() = run {
        val searchResults = mockData.searchResults
        val firstCat = searchResults.cats.first()
        stubFor(post(urlPathMatching(".*/search"))
            .willReturn(ok(searchResults.toJson()))
        )

        val searchScreen = KaspressoSearchScreen()
        searchScreen.findCatWith(firstCat.name)
        searchScreen.checkCatName(firstCat.name, 0)
    }

    @Test
    fun compareSearchResultTest() = run {
        val searchResults = mockData.searchResults
        val firstCat = searchResults.cats.first()
        val catResponse = CatResponseDTO(firstCat)
        val catPhotos = mockData.catEmptyPhotosList

        val searchScreen = KaspressoSearchScreen()
        val catProfileScreen = KaspressoCatProfileScreen()

        stubFor(post(urlPathMatching(".*/search"))
            .willReturn(ok(searchResults.toJson()))
        )

        stubFor(get(urlPathMatching(".*/get-by-id.*"))
            .willReturn(ok(catResponse.toJson()))
        )

        stubFor(get(urlPathMatching(".*/photos"))
                .willReturn(ok(catPhotos.toJson()))
        )

        searchScreen.findCatWith(firstCat.name)
        searchScreen.clickCatAtPosition(0)
        catProfileScreen.compareCatInfoWith(
            firstCat.name,
            firstCat.likes,
            firstCat.description
        )
    }

}