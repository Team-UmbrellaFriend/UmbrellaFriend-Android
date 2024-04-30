package com.sookmyung.umbrellafriend.ui.rental

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivityRentalBinding
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import com.sookmyung.umbrellafriend.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentalActivity :
    BindingActivity<ActivityRentalBinding>(R.layout.activity_rental) {
    private val viewModel by viewModels<RentalViewModel>()
    private lateinit var capture: CaptureManager
    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
        if (result.contents == null) {
            toast("QR을 다시 인식해주세요.")
        }
        else {
            //bottom sheet
            viewModel.extractNumberFromUrl(result.contents)
            showBottomSheet()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = viewModel

        checkCameraPermission()
        scanQRCode()
        capture = CaptureManager(this, binding.bsRental)
        capture.initializeFromIntent(intent, savedInstanceState)
        capture.decode()
    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }
    private fun checkCameraPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.CAMERA
                )
            ) {
                toast(getString(R.string.camera_permission_request))
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    toast(getString(R.string.camera_permission_granted))
                } else {
                    toast(getString(R.string.camera_permission_denied))
                }
            }

            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    private fun scanQRCode() {
        val options = ScanOptions()
        with(options) {
            setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            setCameraId(0)
            setBeepEnabled(false)
            setOrientationLocked(true)
            setPrompt("")
            barcodeLauncher.launch(options)
        }
    }

    private fun showBottomSheet(){
        val bottomSheet = RentalBottomSheet()
        bottomSheet.setStyle(DialogFragment.STYLE_NORMAL, R.style.UmbrellaFriendBottomSheetTheme)
        bottomSheet.show(supportFragmentManager,"RentalBottomSheet")
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 1000
    }
}