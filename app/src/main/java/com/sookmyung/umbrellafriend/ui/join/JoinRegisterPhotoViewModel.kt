package com.sookmyung.umbrellafriend.ui.join

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

class JoinRegisterPhotoViewModel : ViewModel() {
    private val _croppedUri: MutableLiveData<String> = MutableLiveData("")
    val croppedUri: LiveData<String> get() = _croppedUri
    private val _isUriUpdated: MutableLiveData<Boolean> = MutableLiveData(false)
    val isUriUpdated: LiveData<Boolean> get() = _isUriUpdated
    private val _name: MutableLiveData<String> = MutableLiveData("")
    val name: LiveData<String> get() = _name
    private val _studentId: MutableLiveData<String> = MutableLiveData("")
    val studentId: LiveData<String> get() = _studentId

    private val _isExtractFinish: MutableLiveData<Boolean> = MutableLiveData(false)
    val isExtractFinish: LiveData<Boolean> get() = _isExtractFinish

    fun updateUri(data: String) {
        _croppedUri.value = data
        _isUriUpdated.value = true
    }

    fun clearUri() {
        _croppedUri.value = ""
        _isUriUpdated.value = false
    }

    fun extractNumberAndName(input: String) {
        val regex = Regex("(\\p{L}+)\\s*\\((\\d{5,7})\\s*\\)")
        val matchResult = regex.find(input)
        if (matchResult != null) {
            val studentId = matchResult.groupValues[2]
            val name = matchResult.groupValues[1]
            _studentId.value = studentId
            _name.value = name
        } else {
            _studentId.value = ""
            _name.value = ""
        }
        _isExtractFinish.value = true
    }
}