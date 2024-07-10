package com.vl.aviatickets.ui.adapter

import android.annotation.SuppressLint
import coil.load
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.vl.aviatickets.R
import com.vl.aviatickets.databinding.ItemOfferBinding
import com.vl.aviatickets.ui.utils.Formatter
import com.vl.aviatickets.ui.entity.OffersItem

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
            binding.cost.text = context.getString(
                R.string.min_price,
                Formatter.formatPrice(item.offer.price)
            )
        } }
    )