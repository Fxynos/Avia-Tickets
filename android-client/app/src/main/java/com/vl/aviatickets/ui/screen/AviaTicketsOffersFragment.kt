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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.vl.aviatickets.R
import com.vl.aviatickets.databinding.FragmentOffersBinding
import com.vl.aviatickets.ui.adapter.OffersAdapter
import com.vl.aviatickets.ui.hideKeyboard
import com.vl.aviatickets.ui.viewmodel.OffersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AviaTicketsOffersFragment: Fragment() {

    private lateinit var binding: FragmentOffersBinding
    private val viewModel: OffersViewModel by viewModels()
    private val adapter = OffersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOffersBinding.inflate(inflater, parent, false)
        binding.offers.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null)
            binding.inputFrom.setText(viewModel.defaultDepartureTown)

        // when user has to enter arrival town, bottom sheet pops up
        binding.inputTo.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus)
                return@setOnFocusChangeListener

            binding.inputTo.clearFocus()
            hideKeyboard()
            viewModel.chooseDepartureTown(binding.inputFrom.text.toString().trim())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { viewModel.offersState.collect(adapter::setItems) }
                launch { viewModel.validationResultEvents.collect {
                    when (it) {
                        is OffersViewModel.ValidationResult.InvalidTown ->
                            MaterialAlertDialogBuilder(requireActivity())
                                .setTitle(R.string.dialog_invalid_town_title)
                                .setMessage(
                                    if (it.isDepartureTown)
                                        R.string.dialog_invalid_departure_town_message
                                    else
                                        R.string.dialog_invalid_arrival_town_message
                                ).setNeutralButton(R.string.dialog_neutral_button, null)
                                .show()

                        is OffersViewModel.ValidationResult.NavigateToChoosingArrivalTown ->
                            showSearchBottomSheet(it.departureTown)

                        is OffersViewModel.ValidationResult.NavigateToSearch ->
                            findNavController().navigate(
                                AviaTicketsOffersFragmentDirections.actionOffersSearchResults(
                                    it.departureTown,
                                    it.arrivalTown
                                )
                            )
                    }
                }}
            }
        }
    }

    /**
     * Called from [AviaTicketsSearchFragment]
     */
    private fun onSearch(arrivalTown: String) {
        viewModel.chooseArrivalTown(arrivalTown)
    }

    private fun showSearchBottomSheet(departureTown: String) {
        // FIXME backstack duplicates of offers fragment lead to several bottom sheets
        val bottomSheet = AviaTicketsSearchFragment(
            departureTown = departureTown,
            popularDestinations = viewModel.recommendedArrivalTowns,
            onSearch = this::onSearch,
            onClickComplexPath = {
                findNavController().navigate(
                    AviaTicketsOffersFragmentDirections.actionOffersComplexPath()
                )
            },
            onClickWeekend = {
                findNavController().navigate(
                    AviaTicketsOffersFragmentDirections.actionOffersWeekend()
                )
            },
            onClickHotTickets = {
                findNavController().navigate(
                    AviaTicketsOffersFragmentDirections.actionOffersHotTickets()
                )
            }
        )
        bottomSheet.show(parentFragmentManager, null)
    }
}