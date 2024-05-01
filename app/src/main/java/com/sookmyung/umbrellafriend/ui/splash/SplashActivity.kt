package com.sookmyung.umbrellafriend.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivitySplashBinding
import com.sookmyung.umbrellafriend.ui.main.MainActivity
import com.sookmyung.umbrellafriend.util.BindingCustomDialog
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val viewModel by viewModels<SplashViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            observeVersion()
        }, SPLASH_DELAY)
    }

    private fun observeVersion() {
        viewModel.isUpdate.observe(this) {
            checkUpdateState()
        }
    }

    private fun checkUpdateState() {
        when (viewModel.isUpdate.value) {
            SplashViewModel.UpdateType.NONE -> startApp()
            SplashViewModel.UpdateType.FORCE -> showForceUpdateDialog()
            SplashViewModel.UpdateType.RECOMMEND -> showRecommendUpdateDialog()
            else -> {}
        }
    }

    private fun startApp() {
        viewModel.isLogin()
        viewModel.isLogin.observe(this) { isLogin ->
            val intent: Intent = if (isLogin) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, SplashEntryActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun showRecommendUpdateDialog() {
        BindingCustomDialog.Builder().build(
            title = viewModel.version.value?.notificationTitle ?: "새로운 버전 업데이트!",
            subtitle = viewModel.version.value?.notificationContent
                ?: "안정적인 서비스 사용을 위해 최신 버전으로 업데이트 해주세요.",
            btnContent = "업데이트",
            imageDrawable = R.drawable.ic_check,
            btnDoAction = { goToStore() },
            btnBackAction = { startApp() },
            isBackBtn = true
        ).show(supportFragmentManager, "CUSTOM_DIALOG")
    }

    private fun showForceUpdateDialog() {
        BindingCustomDialog.Builder().build(
            title = viewModel.version.value?.notificationTitle ?: "새로운 버전 업데이트!",
            subtitle = viewModel.version.value?.notificationContent
                ?: "안정적인 서비스 사용을 위해 최신 버전으로 업데이트 해주세요.",
            btnContent = "업데이트",
            imageDrawable = R.drawable.ic_check,
            btnDoAction = { goToStore() },
            btnBackAction = {},
            isBackBtn = false
        ).show(supportFragmentManager, "CUSTOM_DIALOG")
    }

    private fun goToStore() {

    }

    companion object {
        const val SPLASH_DELAY = 2000L
    }
}