package com.sookmyung.umbrellafriend.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.FragmentMypageBinding
import com.sookmyung.umbrellafriend.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MypageFragment : BindingFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {
    private val viewModel by viewModels<MypageViewModel>()
    private val mypageHistoryListAdapter: MypageHistoryListAdapter?
        get() = binding.rvMypageRentalHistory.adapter as? MypageHistoryListAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        setMypageHistoryListAdapter()
        setHistoryObserver()
    }

    private fun setMypageHistoryListAdapter() {
        binding.rvMypageRentalHistory.adapter = MypageHistoryListAdapter()
    }

    private fun setHistoryObserver() {
        viewModel.mypage.observe(viewLifecycleOwner) { mypage ->
            mypageHistoryListAdapter?.submitList(mypage.history)
        }
    }
}