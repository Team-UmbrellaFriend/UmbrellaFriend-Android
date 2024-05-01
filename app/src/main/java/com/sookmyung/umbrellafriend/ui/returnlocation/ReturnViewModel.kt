package com.sookmyung.umbrellafriend.ui.returnlocation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sookmyung.umbrellafriend.domain.usecase.GetUmbrellaRentalUseCase
import com.sookmyung.umbrellafriend.domain.usecase.PostUmbrellaRentalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReturnViewModel @Inject constructor(
    val getUmbrellaRentalUseCase: GetUmbrellaRentalUseCase,
    val postUmbrellaRentalUseCase: PostUmbrellaRentalUseCase
) : ViewModel() {
    private val _isReturnAvailable: MutableLiveData<Boolean> = MutableLiveData()
    val isReturnAvailable: LiveData<Boolean> get() = _isReturnAvailable
    private val _isReturn: MutableLiveData<Boolean> = MutableLiveData()
    val isReturn: LiveData<Boolean> get() = _isReturn
    private val _qrLocation: MutableLiveData<String> = MutableLiveData()
    val qrLocation: LiveData<String> get() = _qrLocation

    fun extractLocationFromUrl(url: String) {
        val regex = Regex("return/([^/]+)/")
        val matchResult = regex.find(url)
        val result = matchResult?.value
        val subResult = result?.substringAfter("return/")?.substringBefore("/")
        _qrLocation.value = subResult ?: ""
    }

    fun checkLocationSame(locationName: String) {
        _isReturnAvailable.value = (locationName == qrLocation.value)
    }

    fun returnUmbrella() {
        viewModelScope.launch {
            _isReturn.value = true
        }
    }
}