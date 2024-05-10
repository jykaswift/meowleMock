package ru.tinkoff.fintech.meowle.homework.kaspressoScreens

import android.view.View
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.view.fragments.SearchFragment

class KaspressoSearchScreen: KScreen<KaspressoSearchScreen>() {
    override val layoutId = R.layout.search_fragment
    override val viewClass: Class<*> = SearchFragment().javaClass

    private val searchInput = KEditText { withId(R.id.et_search) }
    private val catsList = KRecyclerView(
        builder = { withId(R.id.rv_search_result_list) },
        itemTypeBuilder = { itemType(::CatItem) }
    )

    fun findCatWith(catName: String) {
        searchInput.replaceText(catName)
        searchInput.pressImeAction()
    }

    fun checkCatName(catName: String, position: Int) {
        catsList.childAt<CatItem>(position) {
            this.catName.containsText(catName)
        }
    }

    fun clickCatAtPosition(position: Int) {
        catsList.childAt<CatItem>(position) {
            click()
        }
    }
}

private class CatItem(matcher: Matcher<View>) : KRecyclerItem<CatItem>(matcher) {
    val catName = KTextView(matcher) { withId(R.id.cat_name) }
}