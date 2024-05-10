package ru.tinkoff.fintech.meowle.homework.dto

import com.google.gson.GsonBuilder

abstract class BaseDTO {
    fun toJson(): String {
        val gson = GsonBuilder().serializeNulls().create()
        return gson.toJson(this)
    }
}