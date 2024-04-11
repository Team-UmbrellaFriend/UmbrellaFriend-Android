package com.sookmyung.umbrellafriend.ui.splash

import android.content.Intent
import android.os.Bundle
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivitySplashEntryBinding
import com.sookmyung.umbrellafriend.ui.join.JoinActivity
import com.sookmyung.umbrellafriend.util.binding.BindingActivity

class SplashEntryActivity :
    BindingActivity<ActivitySplashEntryBinding>(R.layout.activity_splash_entry) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnSplashEntryJoin.setOnClickListener {
            startActivity(Intent(this, JoinActivity::class.java))
        }
    }
}