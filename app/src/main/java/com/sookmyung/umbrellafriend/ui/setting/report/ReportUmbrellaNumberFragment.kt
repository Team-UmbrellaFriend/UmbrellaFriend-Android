package com.sookmyung.umbrellafriend.ui.setting.report

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.FragmentReportUmbrellaNumberBinding
import com.sookmyung.umbrellafriend.ui.setting.report.ReportViewModel.ReportScreen.REPORT
import com.sookmyung.umbrellafriend.util.binding.BindingFragment
import com.sookmyung.umbrellafriend.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportUmbrellaNumberFragment :
    BindingFragment<FragmentReportUmbrellaNumberBinding>(R.layout.fragment_report_umbrella_number) {
    private val viewModel by activityViewModels<ReportViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        setHideKeyBoard()
        checkNextBtnState()
        moveToReport()
    }

    private fun setHideKeyBoard() {
        binding.root.setOnClickListener {
            this.hideKeyboard()
        }
    }

    private fun checkNextBtnState() {
        viewModel.umbrellaNumber.observe(viewLifecycleOwner) {
            viewModel.updateIsUmbrellaNumberBlank()
        }
    }

    private fun moveToReport() {
        binding.btnNext.setOnClickListener {
            viewModel.updateScreenStatus(REPORT)
        }
    }
}