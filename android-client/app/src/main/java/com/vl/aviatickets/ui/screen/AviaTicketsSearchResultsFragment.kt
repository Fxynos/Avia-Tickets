package com.vl.aviatickets.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vl.aviatickets.databinding.FragmentStubBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AviaTicketsSearchResultsFragment: Fragment() {

    private lateinit var binding: FragmentStubBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStubBinding.inflate(inflater, parent, false)
        return binding.root
    }
}