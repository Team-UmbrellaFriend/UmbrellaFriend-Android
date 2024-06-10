package com.sookmyung.umbrellafriend.ui.map

import android.os.Bundle
import androidx.activity.viewModels
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivityUmbrellaMapBinding
import com.sookmyung.umbrellafriend.ui.map.UmbrellaMapViewModel.Companion.ART
import com.sookmyung.umbrellafriend.ui.map.UmbrellaMapViewModel.Companion.MYUNGSIN
import com.sookmyung.umbrellafriend.ui.map.UmbrellaMapViewModel.Companion.RENAISSANCE
import com.sookmyung.umbrellafriend.ui.map.UmbrellaMapViewModel.Companion.SCIENCE
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import com.sookmyung.umbrellafriend.util.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UmbrellaMapActivity :
    BindingActivity<ActivityUmbrellaMapBinding>(R.layout.activity_umbrella_map) {
    private val viewModel by viewModels<UmbrellaMapViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = viewModel

        observeAvailableUmbrellaNum()
        observeClickedBuilding()
        clickBackBtn()
    }

    private fun observeAvailableUmbrellaNum() {
        viewModel.availableUmbrella.observe(this) { num ->
            binding.tvUmbrellaMapRemainCount.text = "${num}개"
            val backgroundResource = when {
                num >= 3 -> R.drawable.shape_mainblue_fill_24_rect
                num >= 1 -> R.drawable.shape_suborange_fill_24_rect
                num == 0 -> R.drawable.shape_error_fill_24_rect
                else -> R.drawable.shape_gray500_fill_24_rect
            }
            binding.tvUmbrellaMapRemainCount.setBackgroundResource(backgroundResource)
        }
    }

    private fun observeClickedBuilding() {
        viewModel.location.observe(this) { _ ->
            when (viewModel.buildingStatus.value) {
                MYUNGSIN -> {
                    setTitle("명신관 우산 잔여 개수", viewModel.location.value ?: "")
                }

                RENAISSANCE -> {
                    setTitle("르네상스관 우산 잔여 개수", viewModel.location.value ?: "")
                }

                SCIENCE -> {
                    setTitle("과학관 우산 잔여 개수", viewModel.location.value ?: "")
                }
                ART -> {
                    setTitle("미술대학 우산 잔여 개수", viewModel.location.value ?: "")
                }
            }
        }
    }

    private fun setTitle(title: String, subTitle: String) {
        binding.tvUmbrellaMapLocationTitle.text = title
        binding.tvUmbrellaMapLocationSubtitle.text = subTitle
    }

    private fun clickBackBtn() {
        binding.ivUmbrellaMapNaviBack.setSingleOnClickListener {
            finish()
        }
    }
}