package com.sookmyung.umbrellafriend.ui.setting.report

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.FragmentReportBinding
import com.sookmyung.umbrellafriend.domain.entity.ReportType
import com.sookmyung.umbrellafriend.domain.entity.ReportType.BROKEN
import com.sookmyung.umbrellafriend.domain.entity.ReportType.ETC
import com.sookmyung.umbrellafriend.domain.entity.ReportType.MISSING
import com.sookmyung.umbrellafriend.domain.entity.ReportType.NONE
import com.sookmyung.umbrellafriend.domain.entity.ReportType.QR
import com.sookmyung.umbrellafriend.util.BindingCustomDialog
import com.sookmyung.umbrellafriend.util.binding.BindingFragment
import com.sookmyung.umbrellafriend.util.hideKeyboard
import com.sookmyung.umbrellafriend.util.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment : BindingFragment<FragmentReportBinding>(R.layout.fragment_report) {
    private val viewModel by activityViewModels<ReportViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        setHideKeyBoard()
        updateView()
        switchReportType()
        handleEtcObservation()
        report()
        finishReport()
    }

    private fun setHideKeyBoard() {
        binding.root.setOnClickListener {
            this.hideKeyboard()
        }
    }

    private fun updateView() {
        val missingViewList = listOf(
            binding.clReportMissingUmbrella,
            binding.tvReportMissingUmbrella,
            binding.ivReportMissingUmbrella
        )
        val brokenViewList = listOf(
            binding.clReportBreakUmbrella,
            binding.tvReportBreakUmbrella,
            binding.ivReportBreakUmbrella
        )
        val qrViewList = listOf(
            binding.clReportBreakQr,
            binding.tvReportBreakQr,
            binding.ivReportBreakQr
        )

        viewModel.reportType.observe(viewLifecycleOwner) { reportType ->
            binding.apply {
                when (reportType) {
                    MISSING -> {
                        setReportClickStyle(missingViewList)
                        setReportNonClickStyle(brokenViewList)
                        setReportNonClickStyle(qrViewList)
                    }

                    BROKEN -> {
                        setReportNonClickStyle(missingViewList)
                        setReportClickStyle(brokenViewList)
                        setReportNonClickStyle(qrViewList)
                    }

                    QR -> {
                        setReportNonClickStyle(missingViewList)
                        setReportNonClickStyle(brokenViewList)
                        setReportClickStyle(qrViewList)
                    }

                    NONE, ETC -> {
                        setReportNonClickStyle(missingViewList)
                        setReportNonClickStyle(brokenViewList)
                        setReportNonClickStyle(qrViewList)
                    }
                }
            }
        }
    }

    private fun setReportNonClickStyle(viewList: List<View>) {
        val clView = viewList[0] as ConstraintLayout
        val tvView = viewList[1] as TextView
        val ivView = viewList[2] as ImageView

        clView.setBackgroundResource(R.drawable.shape_gray100_fill_12_rect)
        tvView.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_500))
        tvView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.gray_100))
        ivView.setImageResource(R.drawable.shape_gray300_fill_4_rect)
    }

    private fun setReportClickStyle(viewList: List<View>) {
        val clView = viewList[0] as ConstraintLayout
        val tvView = viewList[1] as TextView
        val ivView = viewList[2] as ImageView

        clView.setBackgroundResource(R.drawable.shape_lightblue_fill_12_rect)
        tvView.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_blue))
        tvView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_blue))
        ivView.setImageResource(R.drawable.ic_radio_check)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun switchReportType() {
        with(binding) {
            setReportTypeClickListener(clReportMissingUmbrella, MISSING)
            setReportTypeClickListener(clReportBreakUmbrella, BROKEN)
            setReportTypeClickListener(clReportBreakQr, QR)
            etReportEtc.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    hideKeyboard()
                    viewModel.updateReportType(ETC)
                }
                return@setOnTouchListener false
            }
        }
    }

    private fun setReportTypeClickListener(view: View, reportType: ReportType) {
        view.setOnClickListener {
            hideKeyboard()
            if (viewModel.reportType.value == reportType) {
                viewModel.updateReportType(NONE)
            } else {
                viewModel.updateReportType(reportType)
            }
        }
    }


    private fun handleEtcObservation() {
        viewModel.etc.observe(viewLifecycleOwner) { etc ->
            if (etc.isNullOrBlank() && viewModel.reportType.value == ETC) viewModel.updateReportType(
                NONE
            )
            else viewModel.updateReportType(ETC)
        }
    }

    private fun report() {
        binding.btnReport.setSingleOnClickListener {
            viewModel.postReport()
        }
    }

    private fun finishReport() {
        viewModel.isReport.observe(viewLifecycleOwner) { isReport ->
            if (isReport) {
                BindingCustomDialog.Builder().build(
                    title = "신고 접수 완료",
                    subtitle = "신고해 주셔서 감사합니다!\n이른 시일 내에 해결하겠습니다.",
                    btnContent = "확인",
                    imageDrawable = R.drawable.ic_check,
                    btnDoAction = { requireActivity().finish() },
                    btnBackAction = {},
                    isBackBtn = false
                ).show(requireActivity().supportFragmentManager, "CUSTOM_DIALOG")
            } else {
                BindingCustomDialog.Builder().build(
                    title = "신고 접수 실패",
                    subtitle = "신고가 접수되지 않았습니다.\n다시 시도해주시길 바랍니다.",
                    btnContent = "확인",
                    imageDrawable = R.drawable.ic_fail,
                    btnDoAction = { requireActivity().finish() },
                    btnBackAction = {},
                    isBackBtn = false
                ).show(requireActivity().supportFragmentManager, "CUSTOM_DIALOG")
            }
        }
    }
}