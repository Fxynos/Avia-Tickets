package com.vl.aviatickets.ui.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vl.aviatickets.R
import com.vl.aviatickets.databinding.FragmentOffersBinding
import com.vl.aviatickets.domain.entity.Offer
import com.vl.aviatickets.ui.adapter.OffersRecyclerViewAdapter
import com.vl.aviatickets.ui.viewmodel.OffersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AviaTicketsOffersFragment: Fragment() {

    private lateinit var binding: FragmentOffersBinding
    private val viewModel: OffersViewModel by viewModels()
    private lateinit var adapter: OffersRecyclerViewAdapter

    private val isDepartureTownValid: Boolean get() = binding.inputFrom.text.isNotBlank()

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOffersBinding.inflate(inflater, parent, false)
        adapter = OffersRecyclerViewAdapter(requireContext())
        binding.offers.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null)
            binding.inputFrom.setText(viewModel.defaultDepartureTown)

        // when user has to enter arrival town, bottom sheet pops up
        binding.inputTo.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.inputTo.clearFocus()
                viewModel.setDepartureTown(binding.inputFrom.text.toString())
                showSearchBottomSheet()
            }
        }

        lifecycleScope.launch {
            viewModel.offersState.collect {
                adapter.items = it ?: listOf(null, null, null)
                @Suppress("NotifyDataSetChanged")
                adapter.notifyDataSetChanged()
            }
        }
    }

    /**
     * Called from [AviaTicketsSearchFragment]
     */
    private fun onSearch(departureTown: String, arrivalTown: String) {
        findNavController().navigate(AviaTicketsOffersFragmentDirections.actionOffersSearch(
            departureTown,
            arrivalTown
        ))
    }

    /**
     * Check if departure town is valid, show bottom sheet if it is
     */
    private fun showSearchBottomSheet() {
        if (!isDepartureTownValid) {
            binding.inputFrom.requestFocus()
            return
        }

        val bottomSheet = AviaTicketsSearchFragment(
            binding.inputFrom.text.toString(),
            this::onSearch
        )
        bottomSheet.show(parentFragmentManager, null)
    }
}