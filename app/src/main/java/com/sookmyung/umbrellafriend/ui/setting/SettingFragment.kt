package com.sookmyung.umbrellafriend.ui.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.FragmentSettingBinding
import com.sookmyung.umbrellafriend.ui.splash.SplashActivity
import com.sookmyung.umbrellafriend.util.binding.BindingFragment
import com.sookmyung.umbrellafriend.util.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    private val viewModel by viewModels<SettingViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        logout()
        moveToWithdraw()
        moveToReport()
    }

    private fun logout() {
        binding.clSettingLogout.setSingleOnClickListener {
            viewModel.getLogout()
            moveToSplash()
        }
    }

    private fun moveToSplash() {
        val intent = Intent(requireActivity(), SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }

    private fun moveToWithdraw() {
        binding.clSettingWithdraw.setSingleOnClickListener {
//            val fragmentTransaction =
//                requireActivity().supportFragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.fcv_mypage, MypageEditFragment())
//            fragmentTransaction.addToBackStack(null)
//            fragmentTransaction.commit()
        }
    }

    private fun moveToReport() {
        binding.clSettingReport.setSingleOnClickListener {
            val fragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fcv_setting, ReportFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}