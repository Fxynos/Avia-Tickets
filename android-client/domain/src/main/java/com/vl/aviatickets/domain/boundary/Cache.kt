package com.vl.aviatickets.domain.boundary

interface Cache<T: Any> {
    operator fun set(key: T, value: T?)
    operator fun get(key: T): T?
    fun getList(key: T): List<T>?
    fun setList(key: T, list: List<T>?)
}