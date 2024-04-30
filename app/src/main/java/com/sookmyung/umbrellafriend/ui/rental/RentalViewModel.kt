package com.sookmyung.umbrellafriend.ui.rental

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RentalViewModel @Inject constructor(
) : ViewModel() {
    private val _rentalNumber: MutableLiveData<Int> = MutableLiveData()
    val rentalNumber: LiveData<Int> = _rentalNumber
    fun extractNumberFromUrl(url: String) {
        val regex = Regex("/(\\d+)/")
        val matchResult = regex.find(url)
        val result = matchResult?.value.toString()
        val subResult = result.substring(1, result.length - 1)
        _rentalNumber.value = subResult.toInt()
    }

}