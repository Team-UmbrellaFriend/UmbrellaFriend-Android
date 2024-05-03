package com.sookmyung.umbrellafriend.ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.FragmentMypageBinding
import com.sookmyung.umbrellafriend.ui.mypage.MypageViewModel.Companion.USER_ID
import com.sookmyung.umbrellafriend.ui.setting.SettingActivity
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

        moveBack()
        setMypageHistoryListAdapter()
        setHistoryObserver()
        moveToMypageEdit()
        moveToSetting()
    }

    private fun moveBack() {
        binding.ivMypageNaviBack.setOnClickListener {
            requireActivity().finish()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMypageProfile()
    }

    private fun setMypageHistoryListAdapter() {
        binding.rvMypageRentalHistory.adapter = MypageHistoryListAdapter()
    }

    private fun setHistoryObserver() {
        viewModel.mypage.observe(viewLifecycleOwner) { mypage ->
            mypageHistoryListAdapter?.submitList(mypage.history)
        }
    }

    private fun moveToMypageEdit() {
        binding.btnMypageEdit.setSingleOnClickListener {
            val bundle = Bundle().apply {
                putInt(USER_ID, viewModel.mypage.value?.user?.id ?: 0)
            }

            val mypageEditFragment = MypageEditFragment()
            mypageEditFragment.arguments = bundle

            val fragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fcv_mypage, MypageEditFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    private fun moveToSetting() {
        binding.btnMypageSetting.setSingleOnClickListener {
            startActivity(Intent(requireActivity(), SettingActivity::class.java))
        }
    }
}