package com.sookmyung.umbrellafriend.ui.rental

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sookmyung.umbrellafriend.domain.entity.UmbrellaRental
import com.sookmyung.umbrellafriend.domain.usecase.GetUmbrellaRentalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RentalViewModel @Inject constructor(
    val getUmbrellaRentalUseCase: GetUmbrellaRentalUseCase
) : ViewModel() {
    private val _rentalNumber: MutableLiveData<Int> = MutableLiveData()
    private val _umbrellaInfo: MutableLiveData<UmbrellaRental> = MutableLiveData()
    val umbrellaInfo: LiveData<UmbrellaRental> get() = _umbrellaInfo
    private val _isRentalAvailable: MutableLiveData<Boolean> = MutableLiveData()
    val isRentalAvailable: LiveData<Boolean> get() = _isRentalAvailable
    fun extractNumberFromUrl(url: String) {
        val regex = Regex("/(\\d+)/")
        val matchResult = regex.find(url)
        val result = matchResult?.value.toString()
        val subResult = result.substring(1, result.length - 1)
        _rentalNumber.value = subResult.toInt()
        checkIsUmbrellaRentAvailable()
    }

    private fun checkIsUmbrellaRentAvailable() {
        viewModelScope.launch {
            getUmbrellaRentalUseCase(_rentalNumber.value ?: 0)
                .onSuccess {response ->
                    _umbrellaInfo.value = response
                    _isRentalAvailable.value = true
                }
                .onFailure { throwable ->
                    Timber.tag("RENTAL").d(throwable)
                    _isRentalAvailable.value = false
                }
        }
    }
}