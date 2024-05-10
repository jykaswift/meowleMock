package ru.tinkoff.fintech.meowle.homework.composeScreens.elements

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.performClick
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode
import ru.tinkoff.fintech.meowle.homework.utils.NavigationButton
import ru.tinkoff.fintech.meowle.screens.kaspresso.element.SearchBarElement

class BottomNavigation(
    semanticsProvider: SemanticsNodeInteractionsProvider
) : ComposeScreen<SearchBarElement>(
    semanticsProvider = semanticsProvider,
    viewBuilderAction = {
        hasTestTag("navBar")
        useUnmergedTree = true
    }
) {

    fun clickBottomNavigationButton(navigationButton: NavigationButton) {
        val testTag = when (navigationButton) {
            NavigationButton.SEARCH -> "search"
            NavigationButton.RATING -> "rating"
            NavigationButton.ADD -> "addCat"
            NavigationButton.FAVORITE -> "favorites"
            NavigationButton.SETTINGS -> "settings"
        }

        child<KNode> { hasTestTag(testTag) }.performClick()
    }

}