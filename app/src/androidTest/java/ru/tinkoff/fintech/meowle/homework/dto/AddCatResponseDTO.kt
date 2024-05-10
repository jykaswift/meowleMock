package ru.tinkoff.fintech.meowle.homework.dto

data class AddCatResponseDTO(
    val cats: List<CatDTO>
): BaseDTO() {
    constructor(cat: CatDTO): this (
        cats = listOf(cat)
    )
}