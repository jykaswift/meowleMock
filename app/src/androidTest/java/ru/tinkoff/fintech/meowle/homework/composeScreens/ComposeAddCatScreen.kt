package ru.tinkoff.fintech.meowle.homework.composeScreens

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.homework.composeScreens.elements.BottomNavigation
import ru.tinkoff.fintech.meowle.screens.kaspresso.KaspressoComposeSearchScreen

class ComposeAddCatScreen(
    private val semanticsProvider: SemanticsNodeInteractionsProvider
) : ComposeScreen<KaspressoComposeSearchScreen>(semanticsProvider) {

    val bottomNavigation = BottomNavigation(semanticsProvider)
    private val catNameTextField = KNode(semanticsProvider) { hasTestTag("catNameTF") }
    private val catDescriptionTextField = KNode(semanticsProvider) { hasTestTag("catDescriptionTF") }
    private val catGenderMenu = KNode(semanticsProvider) { hasTestTag("genderMenu") }
    private val addButton = KNode(semanticsProvider) { hasTestTag("addButton") }
    private val snackbarMessage = KNode(semanticsProvider) { hasTestTag("snackbarMessage") }

    fun enterName(name: String) {
        catNameTextField.performTextInput(name)
        catNameTextField.performImeAction()
    }

    fun enterDescription(description: String) {
        catDescriptionTextField.performTextInput(description)
        catDescriptionTextField.performImeAction()
    }

    fun setGender(gender: Gender) {
        catGenderMenu.performClick()
        val testTag = when (gender) {
            Gender.MALE -> "maleGender"
            Gender.FEMALE -> "femaleGender"
            Gender.UNISEX -> "unisexGender"
        }

        KNode(semanticsProvider) { hasTestTag(testTag) }
            .performClick()
    }

    fun checkSnackbarMessageWith(text: String) {
        snackbarMessage.assertTextContains(text)
    }

    fun clickAddButton() {
        addButton.performClick()
    }

}