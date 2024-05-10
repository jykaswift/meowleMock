package ru.tinkoff.fintech.meowle.homework.composeScreens

import androidx.compose.ui.semantics.SemanticsNode
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode
import io.github.kakaocup.compose.node.element.lazylist.KLazyListItemNode
import io.github.kakaocup.compose.node.element.lazylist.KLazyListNode
import ru.tinkoff.fintech.meowle.presentation.compose.ui.LazyListItemPosition
import ru.tinkoff.fintech.meowle.screens.kaspresso.KaspressoComposeSearchScreen
import ru.tinkoff.fintech.meowle.screens.kaspresso.element.SearchBarElement

class ComposeSearchScreen(
    semanticsProvider: SemanticsNodeInteractionsProvider
) : ComposeScreen<KaspressoComposeSearchScreen>(semanticsProvider) {

    val searchBar = SearchBarElement(semanticsProvider)

    private val catsList = KLazyListNode(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = {
            hasTestTag("searchCatsList")
            useUnmergedTree = true
        },
        itemTypeBuilder = {
            itemType(::ComposeCatCard)
        },
        positionMatcher = { position -> SemanticsMatcher.expectValue(LazyListItemPosition, position) }
    )

    @OptIn(ExperimentalTestApi::class)
    fun checkCatNameAt(position: Int, catName: String, ) {
        catsList.childAt<ComposeCatCard>(position) {
            this.name.assertTextContains(catName, true)
        }
    }

    @OptIn(ExperimentalTestApi::class)
    fun clickCatAt(position: Int) {
        catsList.childAt<ComposeCatCard>(position) {
            performClick()
        }
    }
}

private class ComposeCatCard(
    semanticsNode: SemanticsNode,
    semanticsProvider: SemanticsNodeInteractionsProvider,
) : KLazyListItemNode<ComposeCatCard>(semanticsNode, semanticsProvider) {
    val name: KNode = child {
        hasTestTag("catName")
        useUnmergedTree = true
    }
}