package com.vl.aviatickets.ui.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vl.aviatickets.R

fun Fragment.hideKeyboard() {
    (
        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    ).hideSoftInputFromWindow(view?.windowToken, 0)
}

fun Fragment.alertInvalidDepartureTown() {
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(R.string.dialog_invalid_town_title)
        .setMessage(R.string.dialog_invalid_departure_town_message)
        .setNeutralButton(R.string.dialog_neutral_button, null)
        .show()
}

fun Fragment.alertInvalidArrivalTown() {
    MaterialAlertDialogBuilder(requireContext())
        .setTitle(R.string.dialog_invalid_town_title)
        .setMessage(R.string.dialog_invalid_arrival_town_message)
        .setNeutralButton(R.string.dialog_neutral_button, null)
        .show()
}