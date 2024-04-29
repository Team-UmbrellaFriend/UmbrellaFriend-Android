package com.sookmyung.umbrellafriend.ui.map

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
import com.sookmyung.umbrellafriend.databinding.ActivityUmbrellaMapBinding
import com.sookmyung.umbrellafriend.domain.entity.RentalStatus.NOT_RENTED
import com.sookmyung.umbrellafriend.domain.entity.RentalStatus.OVERDUE
import com.sookmyung.umbrellafriend.domain.entity.RentalStatus.RENTING
import com.sookmyung.umbrellafriend.ui.mypage.MypageActivity
import com.sookmyung.umbrellafriend.util.BindingCustomDialog
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import com.sookmyung.umbrellafriend.util.setSingleOnClickListener
import com.sookmyung.umbrellafriend.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UmbrellaMapActivity : BindingActivity<ActivityUmbrellaMapBinding>(R.layout.activity_umbrella_map) {
    private val viewModel by viewModels<UmbrellaMapViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = viewModel
    }
}