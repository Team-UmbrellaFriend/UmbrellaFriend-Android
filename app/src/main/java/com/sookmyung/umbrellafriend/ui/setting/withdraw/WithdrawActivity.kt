package com.sookmyung.umbrellafriend.ui.setting.withdraw

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivityWithdrawBinding
import com.sookmyung.umbrellafriend.domain.entity.WithdrawType
import com.sookmyung.umbrellafriend.ui.splash.SplashActivity
import com.sookmyung.umbrellafriend.util.BindingCustomDialog
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import com.sookmyung.umbrellafriend.util.hideKeyboard
import com.sookmyung.umbrellafriend.util.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawActivity : BindingActivity<ActivityWithdrawBinding>(R.layout.activity_withdraw) {

    private val viewModel by viewModels<WithdrawViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        setHideKeyBoard()
        updateView()
        switchWithdrawType()
        handleEtcObservation()
        finishReport()

        binding.btnWithdraw.setSingleOnClickListener {
            viewModel.deleteWithdraw()
        }

    }

    private fun setHideKeyBoard() {
        binding.root.setOnClickListener {
            this.hideKeyboard()
        }
    }

    private fun updateView() {
        val quantityViewList = listOf(
            binding.clWithdrawQuantity,
            binding.tvWithdrawQuantity,
            binding.ivWithdrawQuantity
        )
        val managementViewList = listOf(
            binding.clWithdrawManagement,
            binding.tvWithdrawManagement,
            binding.ivWithdrawManagement
        )
        val newAccountViewList = listOf(
            binding.clWithdrawNewAccount,
            binding.tvWithdrawNewAccount,
            binding.ivWithdrawNewAccount
        )

        viewModel.withdrawType.observe(this) { reportType ->
            binding.apply {
                when (reportType) {
                    WithdrawType.QUANTITY -> {
                        setReportClickStyle(quantityViewList)
                        setReportNonClickStyle(managementViewList)
                        setReportNonClickStyle(newAccountViewList)
                    }

                    WithdrawType.MANAGEMENT -> {
                        setReportNonClickStyle(quantityViewList)
                        setReportClickStyle(managementViewList)
                        setReportNonClickStyle(newAccountViewList)
                    }

                    WithdrawType.NEW_ACCOUNT -> {
                        setReportNonClickStyle(quantityViewList)
                        setReportNonClickStyle(managementViewList)
                        setReportClickStyle(newAccountViewList)
                    }

                    WithdrawType.NONE, WithdrawType.ETC -> {
                        setReportNonClickStyle(quantityViewList)
                        setReportNonClickStyle(managementViewList)
                        setReportNonClickStyle(newAccountViewList)
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
        tvView.setTextColor(ContextCompat.getColor(this, R.color.gray_500))
        tvView.setBackgroundColor(ContextCompat.getColor(this, R.color.gray_100))
        ivView.setImageResource(R.drawable.shape_gray300_fill_4_rect)
    }

    private fun setReportClickStyle(viewList: List<View>) {
        val clView = viewList[0] as ConstraintLayout
        val tvView = viewList[1] as TextView
        val ivView = viewList[2] as ImageView

        clView.setBackgroundResource(R.drawable.shape_lightblue_fill_12_rect)
        tvView.setTextColor(ContextCompat.getColor(this, R.color.main_blue))
        tvView.setBackgroundColor(ContextCompat.getColor(this, R.color.light_blue))
        ivView.setImageResource(R.drawable.ic_radio_check)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun switchWithdrawType() {
        with(binding) {
            setWithdrawTypeClickListener(clWithdrawQuantity, WithdrawType.QUANTITY)
            setWithdrawTypeClickListener(clWithdrawManagement, WithdrawType.MANAGEMENT)
            setWithdrawTypeClickListener(clWithdrawNewAccount, WithdrawType.NEW_ACCOUNT)
            etWithdrawEtc.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    hideKeyboard()
                    viewModel.updateWithdrawType(WithdrawType.ETC)
                }
                return@setOnTouchListener false
            }
        }
    }

    private fun setWithdrawTypeClickListener(view: View, withdrawType: WithdrawType) {
        view.setOnClickListener {
            hideKeyboard()
            if (viewModel.withdrawType.value == withdrawType) {
                viewModel.updateWithdrawType(WithdrawType.NONE)
            } else {
                viewModel.updateWithdrawType(withdrawType)
            }
        }
    }

    private fun handleEtcObservation() {
        viewModel.etc.observe(this) { etc ->
            if (etc.isNullOrBlank() && (viewModel.withdrawType.value == WithdrawType.ETC || viewModel.withdrawType.value == WithdrawType.NONE)) viewModel.updateWithdrawType(
                WithdrawType.NONE
            )
            else viewModel.updateWithdrawType(WithdrawType.ETC)
        }
    }

    private fun finishReport() {
        viewModel.isWithdraw.observe(this) { isWithDraw ->
            when (isWithDraw) {
                true -> {
                    BindingCustomDialog.Builder().build(
                        title = "신고 접수 완료",
                        subtitle = "신고해 주셔서 감사합니다!\n이른 시일 내에 해결하겠습니다.",
                        btnContent = "확인",
                        imageDrawable = R.drawable.ic_check,
                        btnDoAction = {
                            viewModel.withdrawSuccess()
                            val intent = Intent(this, SplashActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY)
                            startActivity(intent)
                        },
                        btnBackAction = {},
                        isBackBtn = false
                    ).show(supportFragmentManager, "CUSTOM_DIALOG")
                }

                false -> {
                    BindingCustomDialog.Builder().build(
                        title = "잠시만요!",
                        subtitle = "우산을 반납하지 않아 탈퇴할 수 없습니다.\n반납 후 다시 진행해 주세요!",
                        btnContent = "확인",
                        imageDrawable = R.drawable.ic_notice,
                        btnDoAction = { finish() },
                        btnBackAction = {},
                        isBackBtn = false
                    ).show(supportFragmentManager, "CUSTOM_DIALOG")
                }
            }
        }
    }
}
