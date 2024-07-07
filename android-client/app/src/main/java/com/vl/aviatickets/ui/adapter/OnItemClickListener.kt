package com.vl.aviatickets.ui.adapter

fun interface OnItemClickListener<T> {
    fun onClick(item: T, position: Int)
}