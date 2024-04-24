package com.sookmyung.umbrellafriend.ui.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sookmyung.umbrellafriend.data.entity.request.EditRequest
import com.sookmyung.umbrellafriend.domain.usecase.GetUserProfileUseCase
import com.sookmyung.umbrellafriend.domain.usecase.PutUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MypageEditViewModel @Inject constructor(
    private val putUserProfileUseCase: PutUserProfileUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase
) : ViewModel() {
    val name: MutableLiveData<String> = MutableLiveData("")
    val studentId: MutableLiveData<String> = MutableLiveData("")
    val phoneNumber: MutableLiveData<String> = MutableLiveData("")
    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")
    val password2: MutableLiveData<String> = MutableLiveData("")
    private val _isEdit: MutableLiveData<Boolean> = MutableLiveData(false)
    val isEdit: LiveData<Boolean> get() = _isEdit
    private val _isEditSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    val isEditSuccess: LiveData<Boolean> get() = _isEditSuccess

    init {
        getUserProfile()
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
        _isEdit.value =
            (!name.value.isNullOrEmpty()) && (!studentId.value.isNullOrEmpty()) && isValidPassword() && isValidPassword2() && isValidPhoneNumber() && (!email.value.isNullOrEmpty())
    }

    private fun parseEmail(email: String): String {
        val atIndex = email.indexOf('@')
        return if (atIndex != -1) {
            email.substring(0, atIndex)
        } else {
            email
        }
    }

    private fun getUserProfile() {
        viewModelScope.launch {
            getUserProfileUseCase(38).onSuccess { response ->
                name.value = response.username
                email.value = parseEmail(response.email)
                studentId.value = response.profile.studentID.toString()
                phoneNumber.value = response.profile.phoneNumber
            }.onFailure { throwable ->
                Timber.e("$throwable")
            }
        }
    }

    fun putProfile() {
        viewModelScope.launch {
            val fullEmail = "${email.value}@sookmyung.ac.kr"

            putUserProfileUseCase(
                userId = 38,
                editRequest = EditRequest(
                    requireNotNull(name.value),
                    fullEmail,
                    requireNotNull(password.value),
                    requireNotNull(password2.value!!),
                    requireNotNull(phoneNumber.value)
                )
            )
                .onSuccess {
                    _isEditSuccess.value = true
                }.onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }
}