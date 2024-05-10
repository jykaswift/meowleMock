package ru.tinkoff.fintech.meowle.homework.dto

import com.google.gson.annotations.SerializedName

data class CatResponseDTO(
    @SerializedName("cat")
    val catDTO: CatDTO
): BaseDTO()