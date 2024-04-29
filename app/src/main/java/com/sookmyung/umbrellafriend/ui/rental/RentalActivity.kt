package com.sookmyung.umbrellafriend.ui.rental

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureManager
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
    private lateinit var qrScan: IntentIntegrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.vm = viewModel

        checkCameraPermission()
        scanQRCode()
        capture = CaptureManager(this, binding.bsRental)
        capture.initializeFromIntent(intent, savedInstanceState)
        capture.decode() //decode
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
        qrScan = IntentIntegrator(this)
        with(qrScan) {
            setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            setCameraId(0)
            captureActivity = RentalActivity::class.java
            setBeepEnabled(false)
            setOrientationLocked(true)
            setPrompt("")
            initiateScan()
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 1000
    }
}