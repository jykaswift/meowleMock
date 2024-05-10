package ru.tinkoff.fintech.meowle.homework.kaspressoScreens

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.bottomnav.KBottomNavigationView
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.homework.utils.NavigationButton

class KaspressoBottomNavigation: KScreen<KaspressoBottomNavigation>() {
    override val layoutId = null
    override val viewClass = null

    private val bottomNavigation = KBottomNavigationView { withId(R.id.bottom_nav) }

    fun clickButton(navigationButton: NavigationButton) {
        val pageId = when (navigationButton) {
            NavigationButton.SEARCH -> R.id.tab_btn_search
            NavigationButton.RATING -> R.id.tab_btn_rating
            NavigationButton.ADD -> R.id.tab_btn_add
            NavigationButton.FAVORITE -> R.id.tab_btn_favourite
            NavigationButton.SETTINGS -> R.id.tab_btn_settings
        }

        bottomNavigation.setSelectedItem(pageId)
    }
}