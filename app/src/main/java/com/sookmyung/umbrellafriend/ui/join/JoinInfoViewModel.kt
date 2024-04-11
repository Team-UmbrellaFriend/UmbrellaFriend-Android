package com.sookmyung.umbrellafriend.ui.join

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class JoinInfoViewModel : ViewModel() {
    val name: MutableLiveData<String> = MutableLiveData("")
    val studentId: MutableLiveData<String> = MutableLiveData("")
    val phoneNumber: MutableLiveData<String> = MutableLiveData("")
    val email: MutableLiveData<String> = MutableLiveData("")
    val password: MutableLiveData<String> = MutableLiveData("")
    val password2: MutableLiveData<String> = MutableLiveData("")
    private val _isJoin: MutableLiveData<Boolean> = MutableLiveData(false)
    val isJoin: LiveData<Boolean> get() = _isJoin

    fun updateStudentCardInfo(n: String, id: String) {
        name.value = n
        studentId.value = id
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
}