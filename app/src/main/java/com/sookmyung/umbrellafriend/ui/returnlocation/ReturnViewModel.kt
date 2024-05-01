package com.sookmyung.umbrellafriend.ui.returnlocation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sookmyung.umbrellafriend.data.entity.request.ReturnRequest
import com.sookmyung.umbrellafriend.domain.usecase.PostUmbrellaReturnUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReturnViewModel @Inject constructor(
    val postUmbrellaReturnUseCase: PostUmbrellaReturnUseCase
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
        val locationKor = when (qrLocation.value) {
            "myungshin" -> "명신관"
            "renaissance" -> "르네상스관"
            "science" -> "과학관"
            else -> {
                ""
            }
        }
        viewModelScope.launch {
            postUmbrellaReturnUseCase(ReturnRequest(locationKor)).onSuccess {
                _isReturn.value = true
            }.onFailure {
                _isReturn.value = false
            }
        }
    }
}