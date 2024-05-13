package ru.tinkoff.fintech.meowle.homework.kaspressoScreens

import android.view.View
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.edit.KTextInputLayout
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.CoreMatchers.not
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.presentation.view.fragments.AddCatFragment

class KaspressoAddCatScreen: KScreen<KaspressoAddCatScreen>() {
    override val layoutId = R.layout.fragment_add
    override val viewClass: Class<*> = AddCatFragment().javaClass
    val bottomNavigation = KaspressoBottomNavigation()

    private val genderTextInputLayout = KTextInputLayout { withId(R.id.til_gender) }
    private val nameEditText = KEditText { withId(R.id.et_name) }
    private val descriptionEditText = KEditText { withId(R.id.cat_description) }
    private val snackbar = KTextView { withText(R.string.add_snackbar_success_message) }
    private val addButton = KButton { withId(R.id.btn_add) }

    fun enterName(name: String) {
        nameEditText.replaceText(name)
    }

    fun enterDescription(description: String) {
        descriptionEditText.replaceText(description)
    }

    fun setGender(gender: Gender) {
        genderTextInputLayout.click()
        val genderText = when (gender) {
            Gender.MALE -> R.string.gender_male
            Gender.FEMALE -> R.string.gender_female
            Gender.UNISEX -> R.string.gender_unisex
        }

        val genderItem = KTextView { withText(genderText) }
        genderItem {
            inRoot { isPlatformPopup() }
            click()
        }
    }

    fun clickAddButton() {
        addButton.click()
    }

    fun checkToast(decorView: View?) {
         snackbar {
             inRoot { withDecorView { not(decorView) } }
             isDisplayed()
         }
    }
}
