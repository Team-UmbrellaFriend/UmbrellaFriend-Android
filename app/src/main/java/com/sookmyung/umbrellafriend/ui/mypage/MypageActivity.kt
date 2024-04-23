package com.sookmyung.umbrellafriend.ui.mypage

import android.os.Bundle
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivityMypageBinding
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MypageActivity : BindingActivity<ActivityMypageBinding>(R.layout.activity_mypage) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFragment()
    }

    private fun initFragment() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_mypage)
        if (currentFragment == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fcv_mypage, MypageFragment())
                .commit()
        }
    }
}