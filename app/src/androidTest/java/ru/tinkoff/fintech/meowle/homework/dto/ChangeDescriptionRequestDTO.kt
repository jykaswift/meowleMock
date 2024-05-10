package ru.tinkoff.fintech.meowle.homework.dto

data class ChangeDescriptionRequestDTO(
    val catDescription: String,
    val catId: Int
): BaseDTO()