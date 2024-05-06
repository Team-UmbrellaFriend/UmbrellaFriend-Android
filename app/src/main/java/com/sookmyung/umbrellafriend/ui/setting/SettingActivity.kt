package com.sookmyung.umbrellafriend.ui.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivitySettingBinding
import com.sookmyung.umbrellafriend.ui.setting.report.ReportActivity
import com.sookmyung.umbrellafriend.ui.setting.withdraw.WithdrawActivity
import com.sookmyung.umbrellafriend.ui.splash.SplashActivity
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import com.sookmyung.umbrellafriend.util.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BindingActivity<ActivitySettingBinding>(R.layout.activity_setting) {

    private val viewModel by viewModels<SettingViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = viewModel
        logout()
        moveToWithdraw()
        moveToReport()
        setupClickListeners()
        close()
    }

    private fun close() {
        binding.ivSettingNaviBack.setOnClickListener {
            finish()
        }
    }


    private fun logout() {
        binding.clSettingLogout.setSingleOnClickListener {
            viewModel.getLogout()
            moveToSplash()
        }
    }


    private fun moveToSplash() {
        val intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }


    private fun moveToWithdraw() {
        binding.clSettingWithdraw.setSingleOnClickListener {
            startActivity(Intent(this, WithdrawActivity::class.java))
        }
    }

    private fun moveToReport() {
        binding.clSettingReport.setSingleOnClickListener {
            startActivity(Intent(this, ReportActivity::class.java))
        }
    }

    private fun setupClickListeners() {
        binding.clSettingServiceTerm.setSingleOnClickListener {
            val url = "https://seed-cantaloupe-7de.notion.site/314515f247014f8d9baf20012b10e944"
            moveToWebView(url)
        }

        binding.clSettingPrivacyPolicy.setSingleOnClickListener {
            val url = "https://seed-cantaloupe-7de.notion.site/a5f353664d1d4196a6abb5ef2943c7e7"
            moveToWebView(url)
        }
    }

    private fun moveToWebView(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}