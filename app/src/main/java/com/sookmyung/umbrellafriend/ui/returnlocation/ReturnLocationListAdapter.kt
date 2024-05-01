package com.sookmyung.umbrellafriend.ui.returnlocation

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
        holder.onBind(getItem(position))
    }


    fun setOnThemeClickListener(listener: (Location) -> Unit) {
        onItemClickListener = listener
    }

    inner class ReturnLocationViewHolder(
        val binding: ItemReturnLocationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(
            data: Location
        ) {
            binding.data = data
            binding.root.setOnClickListener {
                onItemClickListener?.let { it(data) }
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
