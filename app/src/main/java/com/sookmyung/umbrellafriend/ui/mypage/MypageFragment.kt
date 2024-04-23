package com.sookmyung.umbrellafriend.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.FragmentMypageBinding
import com.sookmyung.umbrellafriend.ui.splash.SplashActivity
import com.sookmyung.umbrellafriend.util.binding.BindingFragment
import com.sookmyung.umbrellafriend.util.setSingleOnClickListener
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
        logout()
    }

    private fun setMypageHistoryListAdapter() {
        binding.rvMypageRentalHistory.adapter = MypageHistoryListAdapter()
    }

    private fun setHistoryObserver() {
        viewModel.mypage.observe(viewLifecycleOwner) { mypage ->
            mypageHistoryListAdapter?.submitList(mypage.history)
        }
    }

    private fun logout() {
        binding.btnMypageLogout.setSingleOnClickListener {
            viewModel.getLogout()
            moveToSplash()
        }
    }

    private fun moveToSplash() {
        val intent = Intent(requireActivity(), SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }
}