package com.vl.aviatickets.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.vl.aviatickets.databinding.FragmentStubBinding
import com.vl.aviatickets.databinding.FragmentTicketsBinding
import com.vl.aviatickets.domain.entity.Route
import com.vl.aviatickets.ui.adapter.TicketsAdapter
import com.vl.aviatickets.ui.viewmodel.TicketsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AviaTicketsTicketsFragment: Fragment() {

    private lateinit var binding: FragmentTicketsBinding
    private val viewModel: TicketsViewModel by viewModels()
    private val adapter = TicketsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketsBinding.inflate(inflater, parent, false)
        binding.tickets.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireArguments().apply {
            viewModel.initialize(
                route = Route(getString("departureTown")!!, getString("arrivalTown")!!),
                passengers = getInt("passengersCount").takeIf { it > 0 }!!,
                dateTime = getString("date")!!
            )
        }
        binding.back.setOnClickListener { findNavController().navigateUp() }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(this@AviaTicketsTicketsFragment::updateState)
            }
        }
    }

    private fun updateState(uiState: TicketsViewModel.UiState) {
        binding.route.text = uiState.route
        binding.info.text = uiState.details
        adapter.items = uiState.tickets
    }
}