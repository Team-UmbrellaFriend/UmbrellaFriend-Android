package com.sookmyung.umbrellafriend.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sookmyung.umbrellafriend.domain.usecase.GetLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLoginUseCase: GetLoginUseCase
) : ViewModel() {
    private val _isLogin: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLogin: LiveData<Boolean> get() = _isLogin

    init {
        isLogin()
    }

    private fun isLogin() {
        _isLogin.value = getLoginUseCase()
    }
}