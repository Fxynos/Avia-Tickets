package com.vl.aviatickets.ui.adapter

import android.annotation.SuppressLint
import coil.load
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.vl.aviatickets.R
import com.vl.aviatickets.databinding.ItemOfferBinding
import com.vl.aviatickets.ui.entity.OffersItem
import okhttp3.internal.format

class OffersAdapter: ListDelegationAdapter<List<OffersItem>>(
    loadingOffersAdapterDelegate,
    loadedOffersAdapterDelegate
) {
    @SuppressLint("NotifyDataSetChanged")
    override fun setItems(items: List<OffersItem>?) {
        super.setItems(items)
        notifyDataSetChanged()
    }
}

private const val SHIMMER_STRING_PLACEHOLDER = ""

private val loadingOffersAdapterDelegate: AdapterDelegate<List<OffersItem>>
    get() = adapterDelegateViewBinding<OffersItem.Loading, OffersItem, ItemOfferBinding>(
        viewBinding = { inflater, parent ->
            ItemOfferBinding.inflate(inflater, parent, false)
        },
        block = { bind {
            binding.image.setImageBitmap(null)
            binding.name.text = SHIMMER_STRING_PLACEHOLDER
            binding.location.text = SHIMMER_STRING_PLACEHOLDER
            binding.cost.text = SHIMMER_STRING_PLACEHOLDER
            binding.root.showShimmer(true)
        } }
    )

private val loadedOffersAdapterDelegate: AdapterDelegate<List<OffersItem>>
    get() = adapterDelegateViewBinding<OffersItem.Loaded, OffersItem, ItemOfferBinding>(
        viewBinding = { inflater, parent ->
            ItemOfferBinding.inflate(inflater, parent, false)
        },
        block = { bind {
            val offer = item.offer

            binding.root.hideShimmer()
            binding.image.load(offer.imageUrl) // TODO stop shimmer only after loading photo
            binding.name.text = offer.title
            binding.location.text = offer.town
            binding.cost.text = context.getString(R.string.min_cost, StringBuilder().apply {
                if (offer.price >= 1000) {
                    append(offer.price / 1000)
                    append(format(" %03d", offer.price % 1000))
                } else append(offer.price)
            }.toString())
        } }
    )