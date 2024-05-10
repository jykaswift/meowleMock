package ru.tinkoff.fintech.meowle.homework.dto

data class RatingListDTO(
    val likes: List<RatingLikesCatItemDTO>,
    val dislikes: List<RatingDislikesCatItemDTO>,
    @Transient var cats: List<CatDTO> = listOf()
): BaseDTO() {
    constructor(catDTOList: List<CatDTO>) : this(
        likes = catDTOList.map { RatingLikesCatItemDTO(it.id, it.name, it.likes) },
        dislikes = catDTOList.map { RatingDislikesCatItemDTO(it.id, it.name, it.dislikes) },
        cats = catDTOList
    )
}
