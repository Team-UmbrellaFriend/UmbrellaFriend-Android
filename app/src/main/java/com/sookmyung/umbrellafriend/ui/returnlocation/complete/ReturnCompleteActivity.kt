package com.sookmyung.umbrellafriend.ui.returnlocation.complete

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.ScanOptions
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivityReturnBinding
import com.sookmyung.umbrellafriend.databinding.ActivityReturnCompleteBinding
import com.sookmyung.umbrellafriend.ui.main.MainActivity
import com.sookmyung.umbrellafriend.util.BindingCustomDialog
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import com.sookmyung.umbrellafriend.util.setSingleOnClickListener
import com.sookmyung.umbrellafriend.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReturnCompleteActivity :
    BindingActivity<ActivityReturnCompleteBinding>(R.layout.activity_return_complete) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnGoHome.setSingleOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }
}