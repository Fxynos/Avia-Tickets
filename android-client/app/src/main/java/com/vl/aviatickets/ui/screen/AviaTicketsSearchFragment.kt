package com.vl.aviatickets.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vl.aviatickets.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AviaTicketsSearchFragment(
    private val departureTown: String,
    private val searchListener: OnSearchListener
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

    /**
     * Notifies [AviaTicketsOffersFragment] that arrival town is indicated
     */
    fun interface OnSearchListener {
        fun onSearch(departureTown: String, arrivalTown: String)
    }
}