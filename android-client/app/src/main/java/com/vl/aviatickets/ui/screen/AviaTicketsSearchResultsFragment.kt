package com.vl.aviatickets.ui.screen

import android.animation.ValueAnimator
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.DateSelector
import com.google.android.material.datepicker.MaterialDatePicker
import com.vl.aviatickets.R
import com.vl.aviatickets.databinding.FragmentSearchResultsBinding
import com.vl.aviatickets.domain.entity.Route
import com.vl.aviatickets.ui.adapter.FlightsAdapter
import com.vl.aviatickets.ui.utils.alertInvalidArrivalTown
import com.vl.aviatickets.ui.utils.alertInvalidDepartureTown
import com.vl.aviatickets.ui.utils.hideKeyboard
import com.vl.aviatickets.ui.viewmodel.SearchResultsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AviaTicketsSearchResultsFragment: Fragment() {

    private lateinit var binding: FragmentSearchResultsBinding
    private val viewModel: SearchResultsViewModel by viewModels()
    private val adapter = FlightsAdapter()
    private val swapAnimator = ValueAnimator().apply {
        setFloatValues(0f, 180f)
        interpolator = AccelerateDecelerateInterpolator()
        duration = 200
        repeatCount = 0
        repeatMode = ValueAnimator.RESTART
    }
    private val swapAnimatorListener = ValueAnimator.AnimatorUpdateListener {
        binding.swap.rotation = swapAnimator.animatedValue as Float
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        parent: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultsBinding.inflate(inflater, parent, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.directFlights.adapter = adapter
        swapAnimator.addUpdateListener(swapAnimatorListener)

        sequenceOf(
            binding.back,
            binding.swap,
            binding.clear,
            binding.showAllFlights,
            binding.showAllTickets,
            binding.chipBackwardTicket,
            binding.chipDate,
            binding.chipPassengers
        ).forEach { it.setOnClickListener(this::onClick) }

        // clicked search action on keyboard
        binding.inputTo.setOnEditorActionListener { _, key, _ ->
            if (key == EditorInfo.IME_ACTION_SEARCH)
                search()

            hideKeyboard() // fix: force to hide keyboard when dialog pops up
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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect(
                        this@AviaTicketsSearchResultsFragment::updateState
                    )
                }
                launch {
                    viewModel.searchResultEvent.collect(
                        this@AviaTicketsSearchResultsFragment::handleEvent
                    )
                }
            }
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        // override wrong state saved by fragment, actually savedInstanceState is always null
        binding.from.text = requireArguments().getString("departureTown")
        binding.inputTo.setText(requireArguments().getString("arrivalTown"))
        search()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        swapAnimator.removeUpdateListener(swapAnimatorListener)
    }

    private fun updateState(state: SearchResultsViewModel.UiState) {
        binding.showAllTickets.isEnabled = state.isRouteValid

        binding.chipDateTitle.text = SpannableStringBuilder(
            getString(R.string.flight_date, state.date, state.dayOfWeek)
        ).apply {
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.grey_6)),
                state.date.length,
                state.date.length + state.dayOfWeek.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        binding.chipPassengersTitle.text = getString(
            R.string.passengers_count,
            state.passengers,
            getString(state.seats.title)
        )

        adapter.items = state.flights
    }

    private fun handleEvent(event: SearchResultsViewModel.SearchResult) {
        when (event) {
            is SearchResultsViewModel.SearchResult.InvalidTown ->
                if (event.isDepartureTown)
                    alertInvalidDepartureTown()
                else
                    alertInvalidArrivalTown()

            is SearchResultsViewModel.SearchResult.NavigateToTickets ->
                findNavController().navigate(
                    AviaTicketsSearchResultsFragmentDirections.searchResultsTickets(
                        event.route.departureTown,
                        event.route.arrivalTown,
                        event.date,
                        event.passengers
                    )
                )
        }
    }

    private fun search() = viewModel.search(Route(
        binding.from.text.toString().trim(),
        binding.inputTo.text.toString().trim()
    ))

    private fun onClick(view: View) {
        when (view.id) {
            binding.back.id -> findNavController().navigateUp()
            binding.swap.id -> {
                swapAnimator.cancel()
                swapAnimator.start()

                val (departureTown, arrivalTown) =
                    binding.from.text.toString() to binding.inputTo.text.toString()

                binding.from.text = arrivalTown
                binding.inputTo.setText(departureTown)

                search()
            }
            binding.clear.id -> binding.inputTo.text.clear()
            binding.showAllFlights.id -> Unit // TODO expand flights list
            binding.showAllTickets.id -> viewModel.openTickets()
            binding.chipBackwardTicket.id -> DatePickerDialog(requireContext()).apply {
                setOnDateSetListener { _, y, m, d ->
                    Toast.makeText(requireContext(), "$d.$m.$y", Toast.LENGTH_SHORT).show()
                }
                show()
            }
            binding.chipDate.id -> DatePickerDialog(requireContext()).apply {
                setOnDateSetListener { _, y, m, d -> viewModel.setFlightDate(d, m, y) }
                show()
            }
            binding.chipPassengers.id -> AviaTicketsPassengersDialogFragment { count, seats ->
                viewModel.setPassengers(count, seats)
            }.show(parentFragmentManager, null)
        }
    }
}