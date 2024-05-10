package ru.tinkoff.fintech.meowle.homework.tests.compose

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.equalToJson
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
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.homework.composeScreens.ComposeAddCatScreen
import ru.tinkoff.fintech.meowle.homework.dto.AddCatRequestDTO
import ru.tinkoff.fintech.meowle.homework.dto.AddCatResponseDTO
import ru.tinkoff.fintech.meowle.homework.rules.CleanPreferencesRule
import ru.tinkoff.fintech.meowle.homework.rules.ComposePreferencesRule
import ru.tinkoff.fintech.meowle.homework.rules.MockServerPreferencesRule
import ru.tinkoff.fintech.meowle.homework.utils.MockData
import ru.tinkoff.fintech.meowle.homework.utils.NavigationButton
import ru.tinkoff.fintech.meowle.presentation.MainActivity

class ComposeAddCatTests: TestCase(
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
        val cat = mockData.defaultCat
        val addCatResponse = AddCatResponseDTO(cat)
        val addCatRequestDTO = AddCatRequestDTO(cat)

        val addCatScreen = ComposeAddCatScreen(composeTestRule)

        stubFor(post(urlPathMatching(".*/add"))
            .willReturn(WireMock.ok(addCatResponse.toJson()))
        )

        addCatScreen.bottomNavigation.clickBottomNavigationButton(NavigationButton.ADD)
        addCatScreen.enterName(cat.name)
        addCatScreen.enterDescription(cat.description)
        addCatScreen.setGender(Gender.fromString(cat.gender)!!)
        addCatScreen.clickAddButton()
        addCatScreen.checkSnackbarMessageWith(composeTestRule.activity.getString(R.string.add_snackbar_success_message))
        verify(postRequestedFor(urlPathMatching(".*/add"))
            .withRequestBody(equalToJson(addCatRequestDTO.toJson()))
        )

    }

}