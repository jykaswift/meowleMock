package ru.tinkoff.fintech.meowle.homework.kaspressoScreens

import android.view.View
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.tabs.KTabLayout
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.view.fragments.RatingFragment

class KaspressoRatingScreen: KScreen<KaspressoRatingScreen>() {
    override val layoutId = R.layout.rating_fragment
    override val viewClass: Class<*> = RatingFragment().javaClass

    val bottomNavigation = KaspressoBottomNavigation()
    private val tabLayout = KTabLayout { withId(R.id.tab_layout) }
    private val ratingList = KRecyclerView (
        builder = { withId(R.id.rv_cats_list) },
        itemTypeBuilder = { itemType(::CatRatingItem) }
    )

    fun clickDislikesButton() {
        tabLayout.selectTab(1)
    }

    fun compareCatName(name: String, position: Int) {
        ratingList.childAt<CatRatingItem>(position) {
            this.catName.containsText(name)
        }
    }

    fun clickCatAtPosition(position: Int) {
        ratingList.childAt<CatRatingItem>(position) {
            click()
        }
    }
}

private class CatRatingItem(matcher: Matcher<View>) : KRecyclerItem<CatRatingItem>(matcher) {
    val catName = KTextView(matcher) { withId(R.id.cat_name) }
}