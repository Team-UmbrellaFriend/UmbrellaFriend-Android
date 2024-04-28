package com.sookmyung.umbrellafriend.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.sookmyung.umbrellafriend.data.entity.request.LoginRequest
import com.sookmyung.umbrellafriend.domain.usecase.InitTokenUseCase
import com.sookmyung.umbrellafriend.domain.usecase.PostLoginUseCase
import com.sookmyung.umbrellafriend.domain.usecase.SetLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postLoginUseCase: PostLoginUseCase,
    private val initTokenUseCase: InitTokenUseCase,
    private val setLoginUseCase: SetLoginUseCase
) : ViewModel() {
    lateinit var token: String
    val studentId: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")
    private val _isLoginAvailable: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoginAvailable: LiveData<Boolean> get() = _isLoginAvailable
    private val _isLoginSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoginSuccess: LiveData<Boolean> get() = _isLoginSuccess

    init {
        getToken()
    }

    private fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.e("Fetching FCM registration token failed. ${task.exception}")
                return@OnCompleteListener
            }
            token = task.result
        })
    }

    fun isJoinAvailable() {
        _isLoginAvailable.value =
            (!studentId.value.isNullOrEmpty()) && (!password.value.isNullOrEmpty())
    }

    fun postLogin() {
        viewModelScope.launch {
            postLoginUseCase(
                LoginRequest(
                    studentId.value ?: "", password.value ?: "", token
                )
            )
                .onSuccess { response ->
                    _isLoginSuccess.value = true
                    initTokenUseCase("Token ${response.token}")
                    setLoginUseCase(true)
                }.onFailure { throwable ->
                    _isLoginSuccess.value = false
                    Timber.e("$throwable")
                }
        }
    }
}