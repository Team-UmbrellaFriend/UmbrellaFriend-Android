package com.sookmyung.umbrellafriend.ui.main

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
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = viewModel

        viewModel.home.observe(this) {
            when (viewModel.rentalStatus) {
                NOT_RENTED -> updateViewForNotRented()
                RENTING -> updateViewForRenting()
                OVERDUE -> updateViewForOverdue()
            }
        }
    }

    private fun updateViewForNotRented() {
        updateReturnView(true, "사용한 우산을 반납해요")
        updateRentalView("우산을 대여할 수 있어요", R.drawable.ic_unfold_umbrella)
    }


    private fun updateViewForRenting() {
        updateReturnView(
            false, "사용한 우산을 반납해요", R.color.main_blue, getString(
                R.string.home_return_renting_date,
                viewModel.home.value?.dDay?.daysRemaining.toString()
            )
        )
        updateRentalView("3일 추가로 연장돼요", R.drawable.ic_unfold_umbrella_gray)
        setSpannableBuilder()
    }

    private fun updateViewForOverdue() {
        binding.clHomeUmbrellaRental.isClickable = false
        updateReturnView(
            false, "대여한 우산이 연체되었어요", R.color.sub_orange, getString(
                R.string.home_return_overdue_date,
                viewModel.home.value?.dDay?.daysRemaining.toString()
            )
        )
        updateRentalView("연장할 수 없어요", R.drawable.ic_unfold_umbrella_gray)
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

    private fun updateRentalView(subTitle: String, icon: Int) {
        with(binding) {
            tvHomeUmbrellaRentalSubtitle.text = subTitle
            ivHomeUmbrellaRentalIcon.setImageResource(icon)
        }
    }

    private fun setSpannableBuilder() {
        val indexes = viewModel.findDateIndex()
        if (indexes != null) {
            val ssb = SpannableStringBuilder(viewModel.home.value?.weather?.message)
            ssb.apply {
                setSpan(StyleSpan(Typeface.BOLD), indexes.first, indexes.second, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(UnderlineSpan(), indexes.first, indexes.second, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            binding.tvHomeProfileMention.text = ssb
        }
    }
}