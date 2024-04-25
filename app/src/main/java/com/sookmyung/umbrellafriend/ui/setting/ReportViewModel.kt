package com.sookmyung.umbrellafriend.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sookmyung.umbrellafriend.domain.usecase.GetLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    val getLogoutUseCase: GetLogoutUseCase
) : ViewModel() {
    private val _isReport: MutableLiveData<Boolean> = MutableLiveData(false)
    val isReport: LiveData<Boolean> get() = _isReport

    fun postReport() {
        viewModelScope.launch {
//            getLogoutUseCase()
//                .onSuccess {
//                    Timber.e("logout")
//                }
//                .onFailure { throwable ->
//                    Timber.e("$throwable")
//                }
        }
    }
}