package com.sookmyung.umbrellafriend.ui.setting.withdraw

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sookmyung.umbrellafriend.data.entity.request.WithdrawRequest
import com.sookmyung.umbrellafriend.domain.entity.WithdrawType
import com.sookmyung.umbrellafriend.domain.entity.WithdrawType.ETC
import com.sookmyung.umbrellafriend.domain.entity.WithdrawType.NONE
import com.sookmyung.umbrellafriend.domain.entity.getType
import com.sookmyung.umbrellafriend.domain.usecase.DeleteWithdrawUseCase
import com.sookmyung.umbrellafriend.domain.usecase.InitTokenUseCase
import com.sookmyung.umbrellafriend.domain.usecase.SetLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(
    val deleteWithdrawUseCase: DeleteWithdrawUseCase,
    val initTokenUseCase: InitTokenUseCase,
    val setLoginUseCase: SetLoginUseCase
) : ViewModel() {
    private val _isWithdraw: MutableLiveData<Boolean> = MutableLiveData()
    val isWithdraw: LiveData<Boolean> get() = _isWithdraw
    private val _isNoticeCheck: MutableLiveData<Boolean> = MutableLiveData(false)
    val isNoticeCheck: LiveData<Boolean> get() = _isNoticeCheck
    private val _isWithdrawAvailable: MutableLiveData<Boolean> = MutableLiveData(false)
    val isWithdrawAvailable: LiveData<Boolean> get() = _isWithdrawAvailable
    private val _withdrawType: MutableLiveData<WithdrawType> = MutableLiveData(NONE)
    val withdrawType: LiveData<WithdrawType> get() = _withdrawType
    val etc: MutableLiveData<String> = MutableLiveData("")

    fun updateNoticeCheck() {
        _isNoticeCheck.value = _isNoticeCheck.value?.not()
        updateWithdrawBtnClickable()
    }

    fun updateWithdrawType(withdrawType: WithdrawType) {
        _withdrawType.value = withdrawType
        updateWithdrawBtnClickable()
    }

    private fun updateWithdrawBtnClickable() {
        val isEtcFill = !(_withdrawType.value == ETC && etc.value.isNullOrBlank())

        if (_withdrawType.value == NONE) _isWithdrawAvailable.value = false
        else _isWithdrawAvailable.value = isEtcFill && isNoticeCheck.value == true
    }

    fun deleteWithdraw() {
        viewModelScope.launch {
            deleteWithdrawUseCase(
                WithdrawRequest(
                    isNoticeCheck.value ?: false,
                    withdrawType.value?.getType() ?: "기타",
                    etc.value ?: ""
                )
            ).onSuccess { response ->
                Timber.e(response)
                _isWithdraw.value = true
            }.onFailure { throwable ->
                Timber.e("$throwable")
                _isWithdraw.value = false
            }
        }
    }

    fun withdrawSuccess() {
        initTokenUseCase("")
        setLoginUseCase(false)
    }
}