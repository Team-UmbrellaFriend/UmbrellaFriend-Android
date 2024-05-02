package com.sookmyung.umbrellafriend.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivityLoginBinding
import com.sookmyung.umbrellafriend.ui.main.MainActivity
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import com.sookmyung.umbrellafriend.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        setHideKeyBoard()
        checkIsLoginAvailable()
        login()
    }

    private fun setHideKeyBoard() {
        val hideKeyBoardViewList = listOf(
            binding.root
        )

        hideKeyBoardViewList.map {
            it.setOnClickListener { hideKeyboard() }
        }
    }

    private fun checkIsLoginAvailable() {
        viewModel.studentId.observe(this) {
            viewModel.isJoinAvailable()
            binding.tvLoginError.visibility = View.INVISIBLE
        }
        viewModel.password.observe(this) {
            viewModel.isJoinAvailable()
            binding.tvLoginError.visibility = View.INVISIBLE
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
                    ).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        putExtra("ENTRY",true)
                    })
            }
        }
    }
}