package com.sookmyung.umbrellafriend.ui.setting.report

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivityReportBinding
import com.sookmyung.umbrellafriend.ui.setting.report.ReportViewModel.ReportScreen.REPORT
import com.sookmyung.umbrellafriend.ui.setting.report.ReportViewModel.ReportScreen.REPORT_UMBRELLA_NUMBER
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportActivity : BindingActivity<ActivityReportBinding>(R.layout.activity_report) {

    private val viewModel by viewModels<ReportViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        changeFragment()
        moveBack()
    }

    private fun changeFragment() {
        viewModel.screenStatus.observe(this) { screenStatus ->
            val fragment = when (screenStatus) {
                REPORT -> ReportFragment()
                REPORT_UMBRELLA_NUMBER -> ReportUmbrellaNumberFragment()
            }

            supportFragmentManager.commit {
                replace(R.id.fcv_report, fragment)
                if (screenStatus != REPORT_UMBRELLA_NUMBER) addToBackStack(screenStatus.toString())
            }
        }
    }

    private fun moveBack() {
        binding.ivReportNaviBack.setOnClickListener {
            when (viewModel.screenStatus.value) {
                REPORT -> {
                    supportFragmentManager.popBackStack()
                    viewModel.updateScreenStatus(REPORT_UMBRELLA_NUMBER)
                }
                REPORT_UMBRELLA_NUMBER -> finish()
                else -> finish()
            }

        }
    }
}