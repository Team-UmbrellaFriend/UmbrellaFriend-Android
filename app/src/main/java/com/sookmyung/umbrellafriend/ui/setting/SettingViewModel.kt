package com.sookmyung.umbrellafriend.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sookmyung.umbrellafriend.domain.usecase.GetLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    val getLogoutUseCase: GetLogoutUseCase
) : ViewModel() {

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