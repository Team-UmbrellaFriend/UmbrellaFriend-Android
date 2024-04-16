package com.sookmyung.umbrellafriend.ui.join

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
class JoinInfoViewModel @Inject constructor(
    private val postJoinUseCase: PostJoinUseCase,
    private val initTokenUseCase: InitTokenUseCase
) : ViewModel() {
    private lateinit var imageUri: MultipartBody.Part
    private lateinit var token: String
    val name: MutableLiveData<String> = MutableLiveData("")
    val studentId: MutableLiveData<String> = MutableLiveData("")
    val phoneNumber: MutableLiveData<String> = MutableLiveData("")
    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")
    val password2: MutableLiveData<String> = MutableLiveData("")
    private val _isJoin: MutableLiveData<Boolean> = MutableLiveData(false)
    val isJoin: LiveData<Boolean> get() = _isJoin
    private val _isJoinSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    val isJoinSuccess: LiveData<Boolean> get() = _isJoinSuccess

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

    fun updateStudentCardInfo(n: String, id: String, uri: MultipartBody.Part) {
        name.value = n
        studentId.value = id
        imageUri = uri
    }

    fun isValidPhoneNumber(): Boolean {
        val regex = Regex("^010\\d{8}\$")
        return regex.matches(phoneNumber.value.toString()) && !phoneNumber.value.isNullOrEmpty()
    }

    fun isValidPassword(): Boolean {
        val regex = Regex("^[a-zA-Z0-9]{8,}\$")
        return regex.matches(password.value.toString()) && !password.value.isNullOrEmpty()
    }

    fun isValidPassword2(): Boolean {
        val regex = Regex("^[a-zA-Z0-9]{8,}\$")
        return regex.matches(password2.value.toString()) && password.value.equals(password2.value) && !password2.value.isNullOrEmpty()
    }

    fun isJoinAvailable() {
        _isJoin.value =
            (!name.value.isNullOrEmpty()) && (!studentId.value.isNullOrEmpty()) && isValidPassword() && isValidPassword2() && isValidPhoneNumber() && (!email.value.isNullOrEmpty())
    }

    fun postJoin() {
        viewModelScope.launch {
            val fullEmail = "${email.value}@sookmyung.ac.kr"

            postJoinUseCase(
                studentCard = imageUri,
                body = hashMapOf(
                    "username" to name.value!!.toRequestBody(),
                    "email" to fullEmail.toRequestBody(),
                    "password" to password.value!!.toRequestBody(),
                    "password2" to password2.value!!.toRequestBody(),
                    "profile.studentID" to studentId.value!!.toRequestBody(),
                    "profile.phoneNumber" to phoneNumber.value!!.toRequestBody(),
                    "fcm_token" to token.toRequestBody()
                )
            )
                .onSuccess { response ->
                    initTokenUseCase(response.token)
                    _isJoinSuccess.value = true
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }
    private fun String.toRequestBody(): RequestBody {
        return this.toRequestBody("application/json".toMediaTypeOrNull())
    }
}