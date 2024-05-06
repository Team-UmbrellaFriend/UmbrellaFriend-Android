package com.sookmyung.umbrellafriend.ui.setting.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sookmyung.umbrellafriend.data.entity.request.ReportRequest
import com.sookmyung.umbrellafriend.domain.entity.ReportType
import com.sookmyung.umbrellafriend.domain.entity.ReportType.ETC
import com.sookmyung.umbrellafriend.domain.entity.ReportType.NONE
import com.sookmyung.umbrellafriend.domain.entity.getType
import com.sookmyung.umbrellafriend.domain.usecase.PostReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    val postReportUseCase: PostReportUseCase
) : ViewModel() {
    private val _screenStatus: MutableLiveData<ReportScreen> =
        MutableLiveData(ReportScreen.REPORT_UMBRELLA_NUMBER)
    val screenStatus: LiveData<ReportScreen> get() = _screenStatus
    private val _isNext: MutableLiveData<Boolean> = MutableLiveData(false)
    val isNext: LiveData<Boolean> get() = _isNext
    private val _isReport: MutableLiveData<Boolean> = MutableLiveData(false)
    val isReport: LiveData<Boolean> get() = _isReport
    private val _isReportAvailable: MutableLiveData<Boolean> = MutableLiveData(false)
    val isReportAvailable: LiveData<Boolean> get() = _isReportAvailable
    private val _reportType: MutableLiveData<ReportType> = MutableLiveData(NONE)
    val reportType: LiveData<ReportType> get() = _reportType
    val umbrellaNumber: MutableLiveData<String> = MutableLiveData("")
    val etc: MutableLiveData<String> = MutableLiveData("")

    fun updateIsUmbrellaNumberBlank() {
        _isNext.value = umbrellaNumber.value.isNullOrBlank().not()
    }

    fun updateScreenStatus(screen: ReportScreen) {
        _screenStatus.value = screen
    }

    fun updateReportType(reportType: ReportType) {
        _reportType.value = reportType
        if (_reportType.value == NONE) _isReportAvailable.value = false
        else _isReportAvailable.value = !(_reportType.value == ETC && etc.value.isNullOrBlank())
    }

    fun postReport() {
        viewModelScope.launch {
            postReportUseCase(
                ReportRequest(
                    umbrellaNumber.value ?: "0",
                    reportType.value?.getType() ?: "기타",
                    etc.value ?: ""
                )
            ).onSuccess { response ->
                Timber.e(response)
                _isReport.value = true
            }.onFailure { throwable ->
                Timber.e("$throwable")
                _isReport.value = false
            }
        }
    }

    enum class ReportScreen {
        REPORT_UMBRELLA_NUMBER, REPORT
    }
}