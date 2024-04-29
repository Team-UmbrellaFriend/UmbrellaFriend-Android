package com.sookmyung.umbrellafriend.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sookmyung.umbrellafriend.domain.entity.Home
import com.sookmyung.umbrellafriend.domain.entity.RentalStatus
import com.sookmyung.umbrellafriend.domain.entity.RentalStatus.NOT_RENTED
import com.sookmyung.umbrellafriend.domain.entity.RentalStatus.OVERDUE
import com.sookmyung.umbrellafriend.domain.entity.RentalStatus.RENTING
import com.sookmyung.umbrellafriend.domain.usecase.GetExtendUseCase
import com.sookmyung.umbrellafriend.domain.usecase.GetHomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UmbrellaMapViewModel @Inject constructor(
) : ViewModel() {
}