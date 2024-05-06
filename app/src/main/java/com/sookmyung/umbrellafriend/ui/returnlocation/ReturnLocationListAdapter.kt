package com.sookmyung.umbrellafriend.ui.returnlocation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sookmyung.umbrellafriend.databinding.ItemReturnLocationBinding
import com.sookmyung.umbrellafriend.domain.entity.Location
import com.sookmyung.umbrellafriend.util.ItemDiffCallback

class ReturnLocationListAdapter :
    ListAdapter<Location, ReturnLocationListAdapter.ReturnLocationViewHolder>(DIFF_CALLBACK) {

    private var onItemClickListener: ((Location) -> Unit)? = null
    private var location: String = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReturnLocationViewHolder {
        val itemReturnLocationBinding =
            ItemReturnLocationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ReturnLocationViewHolder(itemReturnLocationBinding)
    }

    override fun onBindViewHolder(holder: ReturnLocationViewHolder, position: Int) {
        val currentItem = getItem(position)
        val updatedItem = if (location == currentItem.locationName) {
            currentItem.copy(isClicked = true)
        } else {
            currentItem.copy(isClicked = false)
        }

        holder.onBind(updatedItem)
    }


    fun setOnThemeClickListener(listener: (Location) -> Unit) {
        onItemClickListener = listener
    }

    inner class ReturnLocationViewHolder(
        val binding: ItemReturnLocationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("NotifyDataSetChanged")
        fun onBind(
            data: Location
        ) {
            binding.data = data
            binding.root.setOnClickListener {
                location = data.locationName
                onItemClickListener?.let { it(data) }
                notifyDataSetChanged()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = ItemDiffCallback<Location>(
            onItemsTheSame = { old, new -> old.locationName == new.locationName },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
