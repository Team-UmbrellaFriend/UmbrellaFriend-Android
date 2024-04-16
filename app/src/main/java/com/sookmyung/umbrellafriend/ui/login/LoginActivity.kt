package com.sookmyung.umbrellafriend.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivityLoginBinding
import com.sookmyung.umbrellafriend.ui.main.MainActivity
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import com.sookmyung.umbrellafriend.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        checkIsLoginAvailable()
        login()
    }

    private fun checkIsLoginAvailable() {
        viewModel.studentId.observe(this) {
            viewModel.isJoinAvailable()
        }
        viewModel.password.observe(this) {
            viewModel.isJoinAvailable()
        }
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            viewModel.postLogin()
            moveToMain()
        }
    }

    private fun moveToMain() {
        viewModel.isLoginSuccess.observe(this) { isLogin ->
            if (isLogin) {
                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                )
            } else {
                toast("로그인에 실패했습니다.") //TODO ask to design
            }
        }
    }
}