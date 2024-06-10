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
    private val _buildingStatus: MutableLiveData<Int> = MutableLiveData()
    val buildingStatus: LiveData<Int> = _buildingStatus
    private val _availableUmbrella: MutableLiveData<Int> = MutableLiveData()
    val availableUmbrella: LiveData<Int> = _availableUmbrella
    private val _location: MutableLiveData<String> = MutableLiveData()
    val location: LiveData<String> = _location

    init {
        getAvailableUmbrella()
    }

    fun updateBuildingStatus(buildingNum: Int) {
        _buildingStatus.value = buildingNum
        val index = (buildingStatus.value?.minus(1)) ?: 0
        val umbrella = _availableUmbrellaList.value?.get(index) ?: AvailableUmbrella(0, "", "", 0)

        updateAvailableUmbrella(umbrella)
        updateLocation(umbrella)
    }

    private fun updateAvailableUmbrella(building: AvailableUmbrella) {
        _availableUmbrella.value = building.numUmbrellas
    }

    private fun updateLocation(building: AvailableUmbrella) {
        val locationName = building.locationName
        val locationDetail = building.locationDetail
        _location.value = "$locationName $locationDetail"
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

    companion object {
        const val MYUNGSIN = 1
        const val RENAISSANCE = 2
        const val SCIENCE = 3
        const val ART = 4
    }
}