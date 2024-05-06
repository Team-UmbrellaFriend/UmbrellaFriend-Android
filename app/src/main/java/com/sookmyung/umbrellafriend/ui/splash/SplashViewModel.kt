package com.sookmyung.umbrellafriend.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sookmyung.umbrellafriend.BuildConfig
import com.sookmyung.umbrellafriend.domain.entity.Version
import com.sookmyung.umbrellafriend.domain.usecase.GetLoginUseCase
import com.sookmyung.umbrellafriend.domain.usecase.GetVersionUseCase
import com.sookmyung.umbrellafriend.domain.usecase.SetUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.pow

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLoginUseCase: GetLoginUseCase,
    private val getVersionUseCase: GetVersionUseCase,
    private val setUpdateUseCase: SetUpdateUseCase
) : ViewModel() {
    private val _isLogin: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLogin: LiveData<Boolean> get() = _isLogin
    private val _isUpdate: MutableLiveData<UpdateType> = MutableLiveData()
    val isUpdate: LiveData<UpdateType> get() = _isUpdate
    private val _version: MutableLiveData<Version> = MutableLiveData()
    val version: LiveData<Version> get() = _version
    private val versionName: String = BuildConfig.VERSION_NAME

    init {
        getVersion()
    }

    private fun getVersion() {
        viewModelScope.launch {
            getVersionUseCase()
                .onSuccess { response ->
                    _version.value = response
                    checkIsUpdate()
                }
                .onFailure { throwable ->
                    Timber.e("$throwable")
                }
        }
    }

    private fun checkIsUpdate() {
        val storeAppVersionNum =
            versionStringToNumber(
                _version.value?.androidVersion?.appVersion ?: MAXIMUM_STORE_VERSION
            )
        val forceAppVersionNum =
            versionStringToNumber(
                _version.value?.androidVersion?.forceUpdateVersion ?: MAXIMUM_FORCE_VERSION
            )
        val versionNameNum = versionStringToNumber(versionName)

        if (versionNameNum < forceAppVersionNum) _isUpdate.value = UpdateType.FORCE
        else if (versionNameNum < storeAppVersionNum) _isUpdate.value = UpdateType.RECOMMEND
        else _isUpdate.value = UpdateType.NONE

        setUpdateUseCase(isUpdate.value == UpdateType.NONE)
    }

    private fun versionStringToNumber(version: String): Int {
        val versionNumbers = version.split(".").map { it.toInt() }
        var result = 0

        for (i in versionNumbers.indices) {
            result += versionNumbers[i] * BASE_POWER.pow((versionNumbers.size - i - 1))
                .toInt()
        }

        return result
    }

    fun isLogin() {
        _isLogin.value = getLoginUseCase()
    }

    enum class UpdateType {
        NONE, FORCE, RECOMMEND
    }


    companion object {
        private const val BASE_POWER = 10.0
        private const val MAXIMUM_STORE_VERSION = "999.9.9"
        private const val MAXIMUM_FORCE_VERSION = "999.0.0"
    }
}