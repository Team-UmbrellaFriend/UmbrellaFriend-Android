package com.sookmyung.umbrellafriend.ui.splash

import android.content.Intent
import android.os.Bundle
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivitySplashEntryBinding
import com.sookmyung.umbrellafriend.ui.join.JoinActivity
import com.sookmyung.umbrellafriend.ui.login.LoginActivity
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashEntryActivity :
    BindingActivity<ActivitySplashEntryBinding>(R.layout.activity_splash_entry) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnSplashEntryJoin.setOnClickListener {
            startActivity(Intent(this, JoinActivity::class.java))
        }

        binding.btnSplashEntryLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}