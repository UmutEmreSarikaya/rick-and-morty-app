package com.umut.rickandmortyapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.umut.rickandmortyapp.databinding.ItemLocationBinding

class LocationListAdapter(val onLocationClick: (Int) -> Unit) :
    RecyclerView.Adapter<LocationListAdapter.LocationListHolder>() {
    private var locations: MutableList<Location?>? = mutableListOf()
    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationListHolder {
        val itemBinding =
            ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationListHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: LocationListHolder, position: Int) {
        holder.bindItems(locations?.get(position), position)
    }

    override fun getItemCount() = locations?.size ?: 0

    inner class LocationListHolder(private val itemBinding: ItemLocationBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        init {
            itemBinding.locationCardView.setOnClickListener {
                selectedPosition = adapterPosition
                notifyDataSetChanged()
                onLocationClick(selectedPosition)
            }
        }

        fun bindItems(location: Location?, position: Int) {
            itemBinding.locationText.text = location?.name
            if (selectedPosition == position) {
                itemBinding.locationCardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.teal_700
                    )

                )
                itemBinding.locationCardView.isClickable = false
            } else {
                itemBinding.locationCardView.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.inverse_text_color
                    )
                )
                itemBinding.locationCardView.isClickable = true
            }
        }
    }

    fun setLocations(locations: MutableList<Location?>?) {
        this.locations = locations
        notifyDataSetChanged()
    }
}