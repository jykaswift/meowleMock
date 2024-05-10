package ru.tinkoff.fintech.meowle.homework.dto

data class CatDTO(
    val id: Int,
    val name: String,
    val description: String,
    val tags: String?,
    val gender: String,
    var likes: Int,
    val dislikes: Int,

) : BaseDTO()

