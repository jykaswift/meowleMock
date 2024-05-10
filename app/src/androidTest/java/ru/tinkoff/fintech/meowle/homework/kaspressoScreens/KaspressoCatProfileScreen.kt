package ru.tinkoff.fintech.meowle.homework.kaspressoScreens

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KTextInputLayout
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import ru.tinkoff.fintech.meowle.R
import ru.tinkoff.fintech.meowle.presentation.view.fragments.DetailsFragment

class KaspressoCatProfileScreen: KScreen<KaspressoCatProfileScreen>() {

    override val layoutId = R.layout.details_fragment_redesign
    override val viewClass: Class<*> = DetailsFragment().javaClass

    private val catName = KTextView { withId(R.id.cat_name) }
    private val catLikes = KTextView { withId(R.id.tw_likes) }
    private val catDescription = KTextView { withId(R.id.cat_description) }
    private val likeButton = KButton { withId(R.id.ib_like) }

    private val editDescriptionButton = KButton { withId(R.id.btn_edit)}
    private val saveDescriptionButton = KButton { withId(R.id.confirm_button)}
    private val descriptionEditField = KTextInputLayout { withId(R.id.til_desc)}.edit

    fun checkCatName(name: String) {
        catName.hasText(name)
    }

    fun checkCatLikesCount(count: Int) {
        catLikes.hasText(count.toString())
    }

    fun checkCatDescription(description: String) {
        catDescription.hasText(description)
    }

    fun clickLikeButton() {
        likeButton.click()
    }

    fun clickEditDescriptionButton() {
        editDescriptionButton.click()
    }

    fun clickSaveDescriptionButton() {
        saveDescriptionButton.click()
    }

    fun changeDescriptionWith(text: String) {
        descriptionEditField.replaceText(text)
    }

    fun compareCatInfoWith(name: String, likesCount: Int, description: String) {
        checkCatName(name)
        checkCatLikesCount(likesCount)
        checkCatDescription(description)
    }

}