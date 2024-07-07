package com.vl.aviatickets.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vl.aviatickets.R
import com.vl.aviatickets.databinding.ItemOfferBinding
import com.vl.aviatickets.domain.entity.Offer
import okhttp3.internal.format

class OffersRecyclerViewAdapter(
    private val context: Context
): RecyclerView.Adapter<OffersRecyclerViewAdapter.ViewHolder>() {

    private var clickListener: OnItemClickListener<Offer>? = null
    var items: List<Offer?> = emptyList()

    fun setOnItemClickListener(listener: OnItemClickListener<Offer>?) {
        this.clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemOfferBinding.inflate(LayoutInflater.from(context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size

    inner class ViewHolder(
        private val binding: ItemOfferBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { onClick() }
        }

        /**
         * @param offer null if loading (shimmer will be shown)
         */
        @SuppressLint("SetTextI18n")
        fun bind(offer: Offer?) {
            if (offer == null) {
                binding.image.setImageBitmap(null)
                binding.name.text = SHIMMER_STRING_PLACEHOLDER
                binding.location.text = SHIMMER_STRING_PLACEHOLDER
                binding.cost.text = SHIMMER_STRING_PLACEHOLDER
                binding.root.showShimmer(true)
            } else {
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
            }
        }

        private fun onClick() {
            val position = adapterPosition
            val item = items[position]
            if (item != null)
                clickListener?.onClick(item, position)
        }
    }
}

private const val SHIMMER_STRING_PLACEHOLDER = ""