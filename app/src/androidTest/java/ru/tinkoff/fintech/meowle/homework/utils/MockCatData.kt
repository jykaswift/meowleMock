package ru.tinkoff.fintech.meowle.homework.utils

import ru.tinkoff.fintech.meowle.domain.cat.Gender
import ru.tinkoff.fintech.meowle.homework.dto.CatDTO

class MockCatData {

    val firstCat = CatDTO(
        1,
        "Коцвунг",
        "Хороший кот",
        null,
        Gender.MALE.gender,
        1000,
        1000,
    )

    val secondCat = CatDTO(
        2,
        "Барс",
        "Плохой кот",
        null,
        Gender.FEMALE.gender,
        900,
        900,
    )

    val thirdCat = CatDTO(
        3,
        "Симба",
        "Король",
        null,
        Gender.MALE.gender,
        800,
        800,
    )

    val fourthCat = CatDTO(
        4,
        "Снежок",
        "Снеговик",
        null,
        Gender.UNISEX.gender,
        700,
        700,
    )

    val fifthCat = CatDTO(
        5,
        "Бобер",
        "Возможно не кот",
        null,
        Gender.MALE.gender,
        600,
        600,
    )

    val sixCat = CatDTO(
        6,
        "Кошак",
        "Просто кот",
        null,
        Gender.FEMALE.gender,
        500,
        500,
    )

    val sevenCat = CatDTO(
        7,
        "Муфаса",
        "Плохой король",
        null,
        Gender.MALE.gender,
        400,
        400,
    )

    val eightCat = CatDTO(
        8,
        "Восьмой",
        "Восьмой",
        null,
        Gender.MALE.gender,
        300,
        300,
    )

    val nineCat = CatDTO(
        9,
        "Птица",
        "Умеет летать",
        null,
        Gender.MALE.gender,
        200,
        200,
    )

    val tenCat = CatDTO(
        10,
        "Спортсмен",
        "быстро бегает",
        null,
        Gender.MALE.gender,
        100,
        100,
    )

    val allCats = listOf(
        firstCat,
        secondCat,
        thirdCat,
        fourthCat,
        fifthCat,
        sixCat,
        sevenCat,
        eightCat,
        nineCat,
        tenCat
    )
}