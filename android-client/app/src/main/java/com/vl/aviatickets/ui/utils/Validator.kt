package com.vl.aviatickets.ui.utils

object Validator {
    fun isTownValid(town: String): Boolean =
        town.isNotBlank() && town.matches(Regex("^[а-яА-Я-]*$"))
}