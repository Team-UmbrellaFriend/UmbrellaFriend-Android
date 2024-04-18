package com.sookmyung.umbrellafriend.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sookmyung.umbrellafriend.domain.entity.Home
import com.sookmyung.umbrellafriend.domain.entity.RentalStatus
import com.sookmyung.umbrellafriend.domain.entity.RentalStatus.NOT_RENTED
import com.sookmyung.umbrellafriend.domain.entity.RentalStatus.OVERDUE
import com.sookmyung.umbrellafriend.domain.entity.RentalStatus.RENTING
import com.sookmyung.umbrellafriend.domain.usecase.GetHomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase
) : ViewModel() {
    private val _home: MutableLiveData<Home> = MutableLiveData()
    val home: LiveData<Home> get() = _home
    var rentalStatus: RentalStatus = NOT_RENTED

    init {
        getHome()
    }

    private fun getHome() {
        viewModelScope.launch {
            getHomeUseCase().onSuccess { response ->
                checkRentalStatus()
                _home.value = response
            }.onFailure { throwable ->
                Timber.e("$throwable")
            }
        }
    }

    private fun checkRentalStatus() {
        val dDay = home.value?.dDay

        rentalStatus = when {
            dDay?.isOverDue == true -> OVERDUE
            dDay?.overdueDays == -1 -> NOT_RENTED
            else -> RENTING
        }
    }

    fun findDateIndex(): Pair<Int, Int>? {
        val text = home.value?.weather?.message
        val charSeq: CharSequence = text ?: ""
        val pattern = "\\b\\d+일\\b".toRegex()
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

    }}