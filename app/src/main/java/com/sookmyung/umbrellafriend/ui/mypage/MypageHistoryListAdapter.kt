package com.sookmyung.umbrellafriend.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sookmyung.umbrellafriend.databinding.ItemRentalHistoryBinding
import com.sookmyung.umbrellafriend.domain.entity.History
import com.sookmyung.umbrellafriend.util.ItemDiffCallback

class MypageHistoryListAdapter :
    ListAdapter<History, MypageHistoryListAdapter.SearchResultViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val itemRentalHistoryBinding =
            ItemRentalHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return SearchResultViewHolder(itemRentalHistoryBinding)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class SearchResultViewHolder(
        val binding: ItemRentalHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(
            data: History
        ) {
            binding.data = data
        }
    }

    companion object {
        private val DIFF_CALLBACK = ItemDiffCallback<History>(
            onItemsTheSame = { old, new -> old.rentDate == new.rentDate },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}
