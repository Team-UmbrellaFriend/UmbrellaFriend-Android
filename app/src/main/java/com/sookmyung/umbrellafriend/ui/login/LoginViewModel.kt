package com.sookmyung.umbrellafriend.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.sookmyung.umbrellafriend.domain.usecase.InitTokenUseCase
import com.sookmyung.umbrellafriend.domain.usecase.PostJoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
//    private val postJoinUseCase: PostJoinUseCase
) : ViewModel() {
    val studentId: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")
    private val _isLoginAvailable: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoginAvailable: LiveData<Boolean> get() = _isLoginAvailable
    private val _isLoginSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoginSuccess: LiveData<Boolean> get() = _isLoginSuccess

    fun isJoinAvailable() {
        _isLoginAvailable.value =
            (!studentId.value.isNullOrEmpty()) && (!password.value.isNullOrEmpty())
    }

    fun postLogin() {
//        viewModelScope.launch {
//            postJoinUseCase()
//                .onSuccess { response ->
//                    _isLoginSuccess.value = true
//                }.onFailure { throwable ->
//                    Timber.e("$throwable")
//                }
    }
}