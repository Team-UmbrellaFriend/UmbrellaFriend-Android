package com.sookmyung.umbrellafriend.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sookmyung.umbrellafriend.domain.entity.Home
import com.sookmyung.umbrellafriend.domain.entity.RentalStatus
import com.sookmyung.umbrellafriend.domain.entity.RentalStatus.NOT_RENTED
import com.sookmyung.umbrellafriend.domain.entity.RentalStatus.NOT_RENTED_OVERDUE
import com.sookmyung.umbrellafriend.domain.entity.RentalStatus.OVERDUE
import com.sookmyung.umbrellafriend.domain.entity.RentalStatus.RENTING
import com.sookmyung.umbrellafriend.domain.usecase.GetExtendUseCase
import com.sookmyung.umbrellafriend.domain.usecase.GetHomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase,
    private val getExtendUseCase: GetExtendUseCase
) : ViewModel() {
    private val _home: MutableLiveData<Home> = MutableLiveData(Home())
    val home: LiveData<Home> get() = _home
    private val _rentalStatus: MutableLiveData<RentalStatus> = MutableLiveData(NOT_RENTED)
    val rentalStatus: LiveData<RentalStatus> get() = _rentalStatus
    private val _isExtended: MutableLiveData<Boolean> = MutableLiveData()
    val isExtended: LiveData<Boolean> get() = _isExtended

    fun getHomeInfo() {
        viewModelScope.launch {
            getHomeUseCase().onSuccess { response ->
                _home.value = response
                checkRentalStatus()
            }.onFailure { throwable ->
                Timber.e("$throwable")
            }
        }
    }

    fun getExtend() {
        viewModelScope.launch {
            getExtendUseCase().onSuccess {
                getHomeInfo()
                _isExtended.value = true
            }.onFailure { throwable ->
                Timber.e("$throwable")
                _isExtended.value = false
            }
        }
    }

    private fun checkRentalStatus() {
        val dDay = home.value?.dDay

        _rentalStatus.value = when {
            dDay?.isOverDue == true && dDay.hasUmbrella -> OVERDUE
            dDay?.isOverDue == true && !dDay.hasUmbrella -> NOT_RENTED_OVERDUE
            dDay?.overdueDays == -1 -> NOT_RENTED
            else -> RENTING
        }
    }

    fun findDateIndex(): Pair<Int, Int>? {
        val text = home.value?.weather?.message
        val charSeq: CharSequence = text ?: ""
        val pattern = "\\d+일".toRegex()
        val matchResult = pattern.find(charSeq)

        return if (matchResult != null) {
            val startIndex = matchResult.range.first
            val endIndex = matchResult.range.last + 1
            Timber.tag(HOME_TAG).d("'n일'의 인덱스: ($startIndex , $endIndex)")
            Pair(startIndex, endIndex)
        } else {
            Timber.tag(HOME_TAG).e("'n일'을 찾을 수 없습니다.")
            null
        }
    }

    companion object {
        const val HOME_TAG = "HOME"

    }
}