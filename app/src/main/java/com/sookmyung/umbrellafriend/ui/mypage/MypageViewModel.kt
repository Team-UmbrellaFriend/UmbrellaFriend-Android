package com.sookmyung.umbrellafriend.ui.mypage

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
class MypageViewModel @Inject constructor(
    val getMypageUseCase: GetMypageUseCase,
    val getLogoutUseCase: GetLogoutUseCase
) : ViewModel() {
    private val _mypage: MutableLiveData<Mypage> = MutableLiveData()
    val mypage: LiveData<Mypage> get() = _mypage
    val isHistoryEmpty: MutableLiveData<Boolean> = MutableLiveData()

    init {
        getMypageProfile()
    }

    fun getMypageProfile() {
        viewModelScope.launch {
            getMypageUseCase()
                .onSuccess { response ->
                    _mypage.value = response
                    isHistoryEmpty.value = response.history.isEmpty()
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