package com.sookmyung.umbrellafriend.ui.join

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class JoinRegisterPhotoViewModel : ViewModel(){
    private val _croppedUri: MutableLiveData<String> = MutableLiveData("")
    val croppedUri: LiveData<String> get() = _croppedUri
    private val _isUriUpdated: MutableLiveData<Boolean> = MutableLiveData(false)
    val isUriUpdated: LiveData<Boolean> get() = _isUriUpdated

    fun updateUri(data: String){
        _croppedUri.value = data
        _isUriUpdated.value = true
    }

    fun clearUri(){
        _croppedUri.value = ""
        _isUriUpdated.value = false
    }
}