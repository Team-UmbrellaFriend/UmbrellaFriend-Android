package com.sookmyung.umbrellafriend.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivitySplashBinding
import com.sookmyung.umbrellafriend.util.binding.BindingActivity

class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
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
//        회원가입 여부에 따른 이동화면 전환
//        val intent: Intent = if (viewModel.isSignedUp() && viewModel.isMember()) {
//            Intent(this, MainActivity::class.java)
//        } else {
//            Intent(this, LoginActivity::class.java)
//        }
        val intent = Intent(this, SplashEntryActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        const val SPLASH_DELAY = 2000L
    }
}