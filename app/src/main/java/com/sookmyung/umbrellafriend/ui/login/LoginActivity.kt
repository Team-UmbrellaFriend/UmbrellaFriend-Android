package com.sookmyung.umbrellafriend.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivityLoginBinding
import com.sookmyung.umbrellafriend.util.binding.BindingActivity

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        login()
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            viewModel.postLogin()
        }
    }
}