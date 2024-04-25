package com.sookmyung.umbrellafriend.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sookmyung.umbrellafriend.domain.entity.Mypage
import com.sookmyung.umbrellafriend.domain.usecase.GetLogoutUseCase
import com.sookmyung.umbrellafriend.domain.usecase.GetMypageUseCase
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

    companion object{
        const val USER_ID = "USER_ID"
    }
}