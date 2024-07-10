package com.vl.aviatickets.ui.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.View
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.vl.aviatickets.R
import com.vl.aviatickets.databinding.ItemFlightBinding
import com.vl.aviatickets.ui.entity.FlightsItem
import com.vl.aviatickets.ui.entity.Formatter

class FlightsAdapter: ListDelegationAdapter<List<FlightsItem>>(
    loadingFlightsAdapterDelegate,
    loadedFlightsAdapterDelegate
) {
    @SuppressLint("NotifyDataSetChanged")
    override fun setItems(items: List<FlightsItem>?) {
        super.setItems(items)
        notifyDataSetChanged()
    }
}

private val loadingFlightsAdapterDelegate: AdapterDelegate<List<FlightsItem>>
    get() = adapterDelegateViewBinding<FlightsItem.Loading, FlightsItem, ItemFlightBinding>(
        viewBinding = { inflater, parent ->
            ItemFlightBinding.inflate(inflater, parent, false)
        },
        block = { bind {
            binding.root.visibility = View.INVISIBLE // TODO shimmer
        }}
    )

private val loadedFlightsAdapterDelegate: AdapterDelegate<List<FlightsItem>>
    get() = adapterDelegateViewBinding<FlightsItem.Loaded, FlightsItem, ItemFlightBinding>(
        viewBinding = { inflater, parent ->
            ItemFlightBinding.inflate(inflater, parent, false)
        },
        block = { bind {
            binding.name.text = item.flight.title
            binding.price.text = context.getString(
                R.string.price,
                Formatter.formatPrice(item.flight.price)
            )
            binding.timetable.text = Formatter.formatTimeRange(item.flight.timeRange)
            binding.indicator.imageTintList = ColorStateList.valueOf(getColor(
                when (absoluteAdapterPosition % 3) {
                    0 -> R.color.red
                    1 -> R.color.blue
                    2 -> R.color.white
                    else -> throw RuntimeException() // unreachable
                }
            ))
        }}
    )