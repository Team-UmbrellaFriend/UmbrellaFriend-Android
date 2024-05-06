package com.sookmyung.umbrellafriend.ui.setting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sookmyung.umbrellafriend.BuildConfig
import com.sookmyung.umbrellafriend.domain.usecase.GetLogoutUseCase
import com.sookmyung.umbrellafriend.domain.usecase.GetUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    val getLogoutUseCase: GetLogoutUseCase,
    val getUpdateUseCase: GetUpdateUseCase
) : ViewModel() {
    private val _update: MutableLiveData<Boolean> = MutableLiveData()
    val update: LiveData<Boolean> get() = _update
    val versionName: String = BuildConfig.VERSION_NAME

    init {
        getUpdate()
    }

    private fun getUpdate() {
        _update.value = getUpdateUseCase()

        Log.e("kang", "getUpdate: ${_update.value}")
    }

    fun getLogout() {
        viewModelScope.launch {
            getLogoutUseCase()
                .onSuccess {
                    Timber.e("logout")
                }
                .onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }
}