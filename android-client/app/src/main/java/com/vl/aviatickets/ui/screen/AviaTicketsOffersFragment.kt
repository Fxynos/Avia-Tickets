package com.vl.aviatickets.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vl.aviatickets.databinding.FragmentOffersBinding

class AviaTicketsOffersFragment: Fragment() {

    private lateinit var binding: FragmentOffersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOffersBinding.inflate(inflater, parent, false)
        return binding.root
    }
}