package com.sookmyung.umbrellafriend.ui.main

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivityMainBinding
import com.sookmyung.umbrellafriend.domain.entity.RentalStatus.NOT_RENTED
import com.sookmyung.umbrellafriend.domain.entity.RentalStatus.OVERDUE
import com.sookmyung.umbrellafriend.domain.entity.RentalStatus.RENTING
import com.sookmyung.umbrellafriend.ui.map.UmbrellaMapActivity
import com.sookmyung.umbrellafriend.ui.mypage.MypageActivity
import com.sookmyung.umbrellafriend.ui.rental.RentalActivity
import com.sookmyung.umbrellafriend.util.BindingCustomDialog
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import com.sookmyung.umbrellafriend.util.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = viewModel

        clickRentalBtn()
        showExtendedDialog()
        checkRentalStatus()
        moveToMypage()
        moveToUmbrellaMap()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getHomeInfo()
    }

    private fun clickRentalBtn() {
        binding.clHomeUmbrellaRental.setSingleOnClickListener {
            when (viewModel.rentalStatus.value) {
                RENTING -> viewModel.getExtend()
                NOT_RENTED -> startActivity(Intent(this, RentalActivity::class.java))
                OVERDUE -> showExtendedFailDialog()
                else -> {}
            }
        }
    }

    private fun showExtendedDialog() {
        viewModel.isExtended.observe(this) { isExtended ->
            if (isExtended) {
                BindingCustomDialog.Builder().build(
                    title = "대여 연장 완료",
                    subtitle = "자동으로 3일이\n추가 연장되었습니다.",
                    btnContent = "확인",
                    imageDrawable = R.drawable.ic_check,
                    btnAction = {}
                ).show(supportFragmentManager, "CUSTOM_DIALOG")
            }
        }
    }

    private fun showExtendedFailDialog() {
        BindingCustomDialog.Builder().build(
            title = "대여 연장 실패",
            subtitle = "연체된 사용자는 연장할 수 없습니다.",
            btnContent = "확인",
            imageDrawable = R.drawable.ic_fail,
            btnAction = {}
        ).show(supportFragmentManager, "CUSTOM_DIALOG")
    }

    private fun checkRentalStatus() {
        viewModel.rentalStatus.observe(this) { rentalStatus ->
            when (rentalStatus) {
                NOT_RENTED -> updateViewForNotRented()
                RENTING -> updateViewForRenting()
                OVERDUE -> updateViewForOverdue()
            }
        }
    }

    private fun updateViewForNotRented() {
        updateReturnView(true, "사용한 우산을 반납해요")
        updateRentalView("우산 대여", "우산을 대여할 수 있어요", R.drawable.ic_unfold_umbrella)
    }


    private fun updateViewForRenting() {
        updateReturnView(
            false, "사용한 우산을 반납해요", R.color.main_blue, getString(
                R.string.home_return_renting_date,
                viewModel.home.value?.dDay?.daysRemaining.toString()
            )
        )
        updateRentalView("대여 연장", "3일 추가로 연장돼요", R.drawable.ic_unfold_umbrella_gray)
        setSpannableBuilder()
    }

    private fun updateViewForOverdue() {
        updateReturnView(
            false, "대여한 우산이 연체되었어요", R.color.sub_orange, getString(
                R.string.home_return_overdue_date,
                viewModel.home.value?.dDay?.overdueDays.toString()
            )
        )
        updateRentalView("대여 불가", "연체해서 대여할 수 없어요", R.drawable.ic_unfold_umbrella_gray)
        setSpannableBuilder()
    }

    private fun updateReturnView(
        iconVisible: Boolean,
        subTitle: String,
        dateColor: Int = R.color.main_blue,
        dateText: String = ""
    ) {
        with(binding.tvHomeUmbrellaReturnDate) {
            visibility = if (iconVisible) View.INVISIBLE else View.VISIBLE
            text = dateText
            setTextColor(ContextCompat.getColor(this@MainActivity, dateColor))
        }

        with(binding) {
            ivHomeUmbrellaReturnIcon.visibility = if (iconVisible) View.VISIBLE else View.INVISIBLE
            tvHomeUmbrellaReturnSubtitle.text = subTitle
        }
    }

    private fun updateRentalView(title: String, subTitle: String, icon: Int) {
        with(binding) {
            tvHomeUmbrellaRentalTitle.text = title
            tvHomeUmbrellaRentalSubtitle.text = subTitle
            ivHomeUmbrellaRentalIcon.setImageResource(icon)
        }
    }

    private fun setSpannableBuilder() {
        val indexes = viewModel.findDateIndex()
        if (indexes != null) {
            val ssb = SpannableStringBuilder(viewModel.home.value?.weather?.message)
            ssb.apply {
                setSpan(
                    StyleSpan(Typeface.BOLD),
                    indexes.first,
                    indexes.second,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                setSpan(
                    UnderlineSpan(),
                    indexes.first,
                    indexes.second,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            binding.tvHomeProfileMention.text = ssb
        }
    }

    private fun moveToMypage() {
        binding.btnMypage.setOnClickListener {
            startActivity(Intent(this, MypageActivity::class.java))
        }
    }

    private fun moveToUmbrellaMap() {
        binding.clHomeMap.setSingleOnClickListener {
            startActivity(Intent(this, UmbrellaMapActivity::class.java))
        }
    }
}