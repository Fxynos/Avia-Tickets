package com.vl.aviatickets.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vl.aviatickets.databinding.FragmentSearchBinding
import com.vl.aviatickets.ui.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AviaTicketsSearchFragment(
    private val departureTown: String,
    private val popularDestinations: List<String>,
    private val onSearch: (arrivalTown: String) -> Unit = {},
    private val onClickComplexPath: () -> Unit = {},
    private val onClickWeekend: () -> Unit = {},
    private val onClickHotTickets: () -> Unit = {}
): BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, parent, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputFrom.setText(departureTown)

        listOf(
            binding.popularTitle1,
            binding.popularTitle2,
            binding.popularTitle3
        ).zip(popularDestinations).forEach { it.first.text = it.second }

        listOf(
            binding.optionComplexRoute,
            binding.optionAnywhere,
            binding.optionWeekend,
            binding.optionHotTickets,
            binding.clear
        ).forEach { it.setOnClickListener(this::onClick) }

        // user returns to choosing departure town
        binding.inputFrom.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                return@setOnFocusChangeListener

            binding.inputFrom.clearFocus()
            hideKeyboard()
            dismiss()
        }

        // clicked search action on keyboard
        binding.inputTo.setOnEditorActionListener { _, key, _ ->
            if (key == EditorInfo.IME_ACTION_SEARCH) {
                dismiss()
                onSearch(binding.inputTo.text.toString().trim())
            }

            false // hide keyboard
        }

        // clear button visibility depends on input text
        binding.inputTo.addTextChangedListener {
            binding.clear.visibility =
                if (binding.inputTo.text.isEmpty())
                    View.INVISIBLE
                else
                    View.VISIBLE
        }

        binding.inputTo.requestFocus()
    }

    private fun onClick(view: View) {
        when (view.id) {
            binding.optionComplexRoute.id -> {
                dismiss()
                onClickComplexPath()
            }
            binding.optionWeekend.id -> {
                dismiss()
                onClickWeekend()
            }
            binding.optionHotTickets.id -> {
                dismiss()
                onClickHotTickets()
            }
            binding.optionAnywhere.id -> {
                binding.inputTo.setText(popularDestinations.random())
            }
            binding.clear.id -> {
                binding.inputTo.text.clear()
            }
        }
    }
}