package com.sookmyung.umbrellafriend.ui.rental

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.ScanOptions
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivityRentalBinding
import com.sookmyung.umbrellafriend.util.BindingCustomDialog
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import com.sookmyung.umbrellafriend.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RentalActivity :
    BindingActivity<ActivityRentalBinding>(R.layout.activity_rental) {
    private val viewModel by viewModels<RentalViewModel>()
    private lateinit var capture: CaptureManager

    private val callback = BarcodeCallback { result: BarcodeResult? ->
        if (result != null) {
            if (result.text == null) {
                toast("QR을 다시 인식해주세요.")
            } else {
                viewModel.extractNumberFromUrl(result.text)
                capture.onPause()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        capture = CaptureManager(this, binding.bsRental)
        capture.initializeFromIntent(intent, savedInstanceState)
        binding.bsRental.decodeContinuous(callback)

        checkCameraPermission()
        scanQRCode()
        checkUmbrellaRentAvailable()
        setRentalListener()
        checkRentSuccess()
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
        }
    }

    private fun checkUmbrellaRentAvailable() {
        viewModel.isRentalAvailable.observe(this) { isRentalAvailable ->
            if (isRentalAvailable) {
                showBottomSheet()
            } else {
                BindingCustomDialog.Builder().build(
                    title = "잠시만요!",
                    subtitle = "해당 우산은 신고가 들어와 대여가 불가해요.\n다른 우산을 대여해주세요.",
                    btnContent = "확인",
                    imageDrawable = R.drawable.ic_notice,
                    btnDoAction = {},
                    btnBackAction = {},
                    isBackBtn = false
                ).show(supportFragmentManager, "CUSTOM_DIALOG")
            }
        }
    }

    private fun showBottomSheet() {
        capture.onPause()
        val bottomSheet = RentalBottomSheet()
        with(bottomSheet) {
            setStyle(DialogFragment.STYLE_NORMAL, R.style.UmbrellaFriendBottomSheetTheme)
            viewModel.umbrellaInfo.value?.let { info ->
                setUmbrellaInfo(info)
            }
            show(supportFragmentManager, "RentalBottomSheet")
        }
    }

    private fun setRentalListener() {
        supportFragmentManager.setFragmentResultListener(
            "RentalBottomSheet",
            this
        ) { _, data ->
            val clickDo = data.getBoolean("isRental")
            if (clickDo) {
                viewModel.rentUmbrella()
            } else {
                capture.onResume()
            }
        }
    }

    private fun checkRentSuccess() {
        viewModel.isRent.observe(this) { isRent ->
            if (isRent) {
                finish()
            } else {
                BindingCustomDialog.Builder().build(
                    title = "잠시만요!",
                    subtitle = "해당 우산은 대여가 불가해요.\n다른 우산을 대여해주세요.",
                    btnContent = "확인",
                    imageDrawable = R.drawable.ic_notice,
                    btnDoAction = { capture.onResume()},
                    btnBackAction = {},
                    isBackBtn = false
                ).show(supportFragmentManager, "CUSTOM_DIALOG")
            }
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 1000
    }
}