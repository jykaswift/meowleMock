package ru.tinkoff.fintech.meowle.homework.dto

data class ChangeRatingDTO(
    val dislike: Boolean,
    val like: Boolean
): BaseDTO()