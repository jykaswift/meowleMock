package ru.tinkoff.fintech.meowle.homework.composeScreens

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode
import ru.tinkoff.fintech.meowle.screens.kaspresso.KaspressoComposeSearchScreen

class ComposeCatProfileScreen(
    semanticsProvider: SemanticsNodeInteractionsProvider
) : ComposeScreen<KaspressoComposeSearchScreen>(semanticsProvider) {

    private val catName = KNode(semanticsProvider) { hasTestTag("catName") }
    private val likeButton = KNode(semanticsProvider) { hasTestTag("likeButton") }
    private val catDescriptionTextView = KNode(semanticsProvider) { hasTestTag("catDescription") }
    private val catLikesCount = KNode(semanticsProvider) { hasTestTag("catLikes") }

    private val editDescriptionButton = KNode(semanticsProvider) { hasTestTag("editDescriptionButton") }
    private val saveDescriptionButton = KNode(semanticsProvider) { hasTestTag("saveDescriptionButton") }
    private val descriptionTextField = KNode(semanticsProvider) { hasTestTag("catDescriptionTF") }


    fun clickEditDescriptionButton() {
        editDescriptionButton.performClick()
    }

    fun clickSaveDescriptionButton() {
        saveDescriptionButton.performClick()
    }

    fun changeDescriptionWith(text: String) {
        descriptionTextField.performTextInput(text)
    }

    fun compareCatNameWith(name: String) {
        catName.assertTextContains(name, true)
    }

    fun compareCatDescriptionWith(description: String) {
        catDescriptionTextView.assertTextContains(description, true)
    }

    fun compareCatLikesCountWith(count: Int) {
        catLikesCount.assertTextContains(count.toString(), true)
    }

    fun clickLikeButton() {
        likeButton.performClick()
    }
}