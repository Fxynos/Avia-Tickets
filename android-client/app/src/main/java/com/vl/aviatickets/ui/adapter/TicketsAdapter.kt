package com.vl.aviatickets.ui.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.View
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.vl.aviatickets.R
import com.vl.aviatickets.databinding.ItemTicketBinding
import com.vl.aviatickets.ui.utils.Formatter
import com.vl.aviatickets.ui.entity.TicketsItem

class TicketsAdapter: ListDelegationAdapter<List<TicketsItem>>(
    loadingTicketsAdapterDelegate,
    loadedTicketsAdapterDelegate
) {
    @SuppressLint("NotifyDataSetChanged")
    override fun setItems(items: List<TicketsItem>?) {
        super.setItems(items)
        notifyDataSetChanged()
    }
}

private val loadingTicketsAdapterDelegate: AdapterDelegate<List<TicketsItem>>
    get() = adapterDelegateViewBinding<TicketsItem.Loading, TicketsItem, ItemTicketBinding>(
        viewBinding = { inflater, parent ->
            ItemTicketBinding.inflate(inflater, parent, false)
        },
        block = { bind {
            binding.root.visibility = View.GONE
        } }
    )

private val loadedTicketsAdapterDelegate: AdapterDelegate<List<TicketsItem>>
    get() = adapterDelegateViewBinding<TicketsItem.Loaded, TicketsItem, ItemTicketBinding>(
        viewBinding = { inflater, parent ->
            ItemTicketBinding.inflate(inflater, parent, false)
        },
        block = { bind {
            val ticket = item.ticket

            binding.root.visibility = View.VISIBLE

            binding.price.text = getString(R.string.price, Formatter.formatPrice(ticket.price))

            binding.departureTime.text =
                Formatter.formatUserTime(Formatter.parseUnixTime(ticket.departureTime))
            binding.arrivalTime.text =
                Formatter.formatUserTime(Formatter.parseUnixTime(ticket.arrivalTime))
            binding.duration.text = Formatter.formatDuration(
                durationSec = (ticket.arrivalTime - ticket.departureTime).toInt(),
                hasTransfer = ticket.hasTransfer
            )

            binding.departureAirport.text = ticket.departureAirport
            binding.arrivalAirport.text = ticket.arrivalAirport

            binding.badge.visibility = if (ticket.badge == null) View.GONE else View.VISIBLE
            binding.badgeTitle.text = ticket.badge

            binding.indicator.imageTintList = ColorStateList.valueOf(getColor(
                when (absoluteAdapterPosition % 3) {
                    0 -> R.color.red
                    1 -> R.color.blue
                    2 -> R.color.white
                    else -> throw RuntimeException() // unreachable
                }
            ))
        } }
    )