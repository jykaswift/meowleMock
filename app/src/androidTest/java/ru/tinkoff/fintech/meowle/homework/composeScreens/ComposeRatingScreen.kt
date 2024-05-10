package ru.tinkoff.fintech.meowle.homework.composeScreens

import androidx.compose.ui.semantics.SemanticsNode
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode
import io.github.kakaocup.compose.node.element.lazylist.KLazyListItemNode
import io.github.kakaocup.compose.node.element.lazylist.KLazyListNode
import ru.tinkoff.fintech.meowle.homework.composeScreens.elements.BottomNavigation
import ru.tinkoff.fintech.meowle.presentation.compose.ui.LazyListItemPosition
import ru.tinkoff.fintech.meowle.screens.kaspresso.KaspressoComposeSearchScreen

class ComposeRatingScreen(
    semanticsProvider: SemanticsNodeInteractionsProvider
) : ComposeScreen<KaspressoComposeSearchScreen>(semanticsProvider) {

    val bottomNavigation = BottomNavigation(semanticsProvider)
    private val dislikesButton = KNode(semanticsProvider) {
        hasTestTag("dislikesButton")
    }

    fun clickDislikesButton() {
        dislikesButton.performClick()
    }

    private val ratingList = KLazyListNode(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = {
            hasTestTag("ratingCatList")
            useUnmergedTree = true
        },
        itemTypeBuilder = {
            itemType(::ComposeRatingCatCard)
        },
        positionMatcher = { position -> SemanticsMatcher.expectValue(LazyListItemPosition, position) }
    )

    @OptIn(ExperimentalTestApi::class)
    fun clickCatAt(position: Int) {
        ratingList.childAt<ComposeRatingCatCard>(position) {
            performClick()
        }
    }

    @OptIn(ExperimentalTestApi::class)
    fun compareCatNameAt(position: Int, name: String) {
        ratingList.childAt<ComposeRatingCatCard>(position) {
            this.catName.assertTextContains(name, true)
        }
    }
}

private class ComposeRatingCatCard(
    semanticsNode: SemanticsNode,
    semanticsProvider: SemanticsNodeInteractionsProvider,
) : KLazyListItemNode<ComposeRatingCatCard>(semanticsNode, semanticsProvider) {
    val catName: KNode = child {
        hasTestTag("catName")
        useUnmergedTree = true
    }
}