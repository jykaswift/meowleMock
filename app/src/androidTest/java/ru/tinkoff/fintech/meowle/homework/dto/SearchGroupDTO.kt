package ru.tinkoff.fintech.meowle.homework.dto

data class SearchGroupDTO(
    val title: String,
    val cats: List<CatDTO>,
    val count: Int
)