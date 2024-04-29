package com.sookmyung.umbrellafriend.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sookmyung.umbrellafriend.domain.entity.AvailableUmbrella
import com.sookmyung.umbrellafriend.domain.usecase.GetAvailableUmbrellaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UmbrellaMapViewModel @Inject constructor(
    val getAvailableUmbrellaUseCase: GetAvailableUmbrellaUseCase
) : ViewModel() {
    private val _availableUmbrellaList: MutableLiveData<List<AvailableUmbrella>> = MutableLiveData()
    val availableUmbrellaList: LiveData<List<AvailableUmbrella>> get() = _availableUmbrellaList

    init {
        getAvailableUmbrella()
    }

    private fun getAvailableUmbrella() {
        viewModelScope.launch {
            getAvailableUmbrellaUseCase()
                .onSuccess { response ->
                    _availableUmbrellaList.value = response
                }
                .onFailure { throwable ->
                    Timber.tag("availableUmbrella").d("$throwable")
                }
        }
    }
}