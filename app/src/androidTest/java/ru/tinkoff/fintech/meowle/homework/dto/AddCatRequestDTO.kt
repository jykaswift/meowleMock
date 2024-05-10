package ru.tinkoff.fintech.meowle.homework.dto

data class AddCatRequestDTO(
    val cats: List<AddCatDTO>
): BaseDTO() {
    data class AddCatDTO(
        val description: String,
        val name: String,
        val gender: String
    )

    constructor(cat: CatDTO) : this (
        cats = listOf(AddCatDTO(cat.description, cat.name, cat.gender))
    )
}
