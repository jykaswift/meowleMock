package ru.tinkoff.fintech.meowle.homework.utils

import ru.tinkoff.fintech.meowle.homework.dto.CatPhotosDTO
import ru.tinkoff.fintech.meowle.homework.dto.RatingListDTO
import ru.tinkoff.fintech.meowle.homework.dto.SearchResultsDTO

class MockData {

    private val mockCatData = MockCatData()

    val defaultCat = mockCatData.firstCat

    val cats = mockCatData.allCats

    val catEmptyPhotosList = CatPhotosDTO(listOf())

    val ratingList = RatingListDTO(catDTOList = cats)

    val searchResults = SearchResultsDTO(cats)



}