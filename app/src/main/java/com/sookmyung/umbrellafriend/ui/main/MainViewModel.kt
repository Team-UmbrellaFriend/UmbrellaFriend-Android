package com.sookmyung.umbrellafriend.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sookmyung.umbrellafriend.domain.entity.Home
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

    init {
        getHome()
    }

    private fun getHome() {
        viewModelScope.launch {
            getHomeUseCase().onSuccess { response ->
                _home.value = response
            }.onFailure { throwable ->
                Timber.e("$throwable")
            }
        }
    }
}