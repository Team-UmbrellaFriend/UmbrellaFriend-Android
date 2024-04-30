package com.sookmyung.umbrellafriend.ui.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sookmyung.umbrellafriend.domain.entity.Version
import com.sookmyung.umbrellafriend.domain.usecase.GetLoginUseCase
import com.sookmyung.umbrellafriend.domain.usecase.GetVersionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLoginUseCase: GetLoginUseCase,
    private val getVersionUseCase: GetVersionUseCase
) : ViewModel() {
    private val _isLogin: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLogin: LiveData<Boolean> get() = _isLogin
    private val _version: MutableLiveData<Version> = MutableLiveData()
    val version: LiveData<Version> get() = _version

    init {
        getVersion()
        isLogin()
    }

    private fun getVersion() {
        viewModelScope.launch {
            getVersionUseCase()
                .onSuccess { response ->
                    _version.value = response
                }
                .onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }

    private fun isLogin() {
        _isLogin.value = getLoginUseCase()
    }
}