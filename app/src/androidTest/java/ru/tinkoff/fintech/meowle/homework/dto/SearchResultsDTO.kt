package ru.tinkoff.fintech.meowle.homework.dto

data class SearchResultsDTO(
    val groups: List<SearchGroupDTO>,
    val count: Int,
    @Transient var cats: List<CatDTO> = listOf()
): BaseDTO() {
    constructor(catDTOList: List<CatDTO>) : this(
        groups = catDTOList.groupBy { it.name.first().toString().toUpperCase() }
            .map { (firstLetter, cats) ->
                SearchGroupDTO(
                    title = firstLetter,
                    cats = cats,
                    count = cats.size
                )
            },
        count = catDTOList.size,
        cats = catDTOList
    )
}