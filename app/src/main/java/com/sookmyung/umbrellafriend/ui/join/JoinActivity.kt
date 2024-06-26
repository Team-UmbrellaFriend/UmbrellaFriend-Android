package com.sookmyung.umbrellafriend.ui.join

import android.os.Bundle
import androidx.fragment.app.commit
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivityJoinBinding
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinActivity : BindingActivity<ActivityJoinBinding>(R.layout.activity_join) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initFragment()
        moveBack()
    }

    private fun initFragment() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_join)
        if (currentFragment == null) {
            supportFragmentManager.commit {
                replace(R.id.fcv_join, JoinRegisterPhotoFragment())
            }
        }
    }

    private fun moveBack() {
        binding.ivJoinNaviBack.setOnClickListener { finish() }
    }
}