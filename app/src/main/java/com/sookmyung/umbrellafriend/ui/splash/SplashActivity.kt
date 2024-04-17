package com.sookmyung.umbrellafriend.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivitySplashBinding
import com.sookmyung.umbrellafriend.ui.main.MainActivity
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    private val viewModel by viewModels<SplashViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                startApp()
//                업데이트 버전 관리 로직
//                when (viewModel.isUpdate.value) {
//                    UpdateType.NONE -> startApp()
//                    UpdateType.FORCE -> showForceUpdateDialog()
//                    UpdateType.RECOMMEND -> showRecommendUpdateDialog()
//                    else -> {}
//                }
            }, SPLASH_DELAY
        )
    }

    private fun startApp() {
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

    companion object {
        const val SPLASH_DELAY = 2000L
    }
}