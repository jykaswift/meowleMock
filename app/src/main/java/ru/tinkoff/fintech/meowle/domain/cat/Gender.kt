package ru.tinkoff.fintech.meowle.domain.cat

/**
 * @author Ruslan Ganeev
 */
enum class Gender(val gender: String) {
    MALE("male"),
    FEMALE("female"),
    UNISEX("unisex");

    companion object {
        fun fromString(gender: String): Gender? {
            return entries.find { it.gender == gender }
        }
    }
}
