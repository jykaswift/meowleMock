package ru.tinkoff.fintech.meowle.homework.tests.kaspresso


import android.view.View
import androidx.test.ext.junit.rules.activityScenarioRule
import com.github.tomakehurst.wiremock.client.WireMock.equalToJson
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
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.homework.dto.AddCatRequestDTO
import ru.tinkoff.fintech.meowle.homework.dto.AddCatResponseDTO
import ru.tinkoff.fintech.meowle.homework.kaspressoScreens.KaspressoAddCatScreen
import ru.tinkoff.fintech.meowle.homework.rules.CleanPreferencesRule
import ru.tinkoff.fintech.meowle.homework.rules.MockServerPreferencesRule
import ru.tinkoff.fintech.meowle.homework.utils.MockData
import ru.tinkoff.fintech.meowle.homework.utils.NavigationButton
import ru.tinkoff.fintech.meowle.presentation.MainActivity


class KaspressoAddCatTests: TestCase(
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

    private var decorView: View? = null
    @Test
    fun addCatTest(): Unit = before {
        activityScenarioRule.scenario.onActivity { activity -> decorView = activity.window.decorView }
    }.run {
        val cat = mockData.defaultCat
        val addCatResponse = AddCatResponseDTO(cat)
        val addCatRequestDTO = AddCatRequestDTO(cat)

        val addCatScreen = KaspressoAddCatScreen()

        stubFor(post(urlPathMatching(".*/add"))
            .willReturn(ok(addCatResponse.toJson()))
        )

        addCatScreen.bottomNavigation.clickButton(NavigationButton.ADD)
        addCatScreen.enterName(cat.name)
        addCatScreen.enterDescription(cat.description)
        addCatScreen.setGender(Gender.fromString(cat.gender)!!)
        addCatScreen.clickAddButton()

        verify(postRequestedFor(urlPathMatching(".*/add"))
            .withRequestBody(equalToJson(addCatRequestDTO.toJson()))
        )

//        Для API < 30
//        addCatScreen.checkToast(decorView)
    }
}