package com.vl.aviatickets.ui

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    (
        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    ).hideSoftInputFromWindow(view?.windowToken, 0)
}