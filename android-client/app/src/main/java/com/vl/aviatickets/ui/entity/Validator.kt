package com.vl.aviatickets.ui.entity

object Validator {
    fun isTownValid(town: String): Boolean =
        town.isNotBlank() && town.matches(Regex("^[а-яА-Я-]*$"))
}