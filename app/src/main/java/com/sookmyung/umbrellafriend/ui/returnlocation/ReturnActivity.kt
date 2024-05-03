package com.sookmyung.umbrellafriend.ui.returnlocation

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
import com.sookmyung.umbrellafriend.ui.returnlocation.complete.ReturnCompleteActivity
import com.sookmyung.umbrellafriend.util.BindingCustomDialog
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import com.sookmyung.umbrellafriend.util.setSingleOnClickListener
import com.sookmyung.umbrellafriend.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReturnActivity :
    BindingActivity<ActivityReturnBinding>(R.layout.activity_return) {
    private val viewModel by viewModels<ReturnViewModel>()
    private lateinit var capture: CaptureManager

    private val callback = BarcodeCallback { result: BarcodeResult? ->
        if (result != null) {
            if (result.text == null) {
                toast("QR을 다시 인식해주세요.")
            } else {
                capture.onPause()
                viewModel.extractLocationFromUrl(result.text)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        capture = CaptureManager(this, binding.bsReturn)
        capture.initializeFromIntent(intent, savedInstanceState)
        binding.bsReturn.decodeContinuous(callback)


        clickHelp()
        checkCameraPermission()
        scanQRCode()
        scanQrSuccess()
        checkLocationSame()
        setReturnBtnClickListener()
        checkRentSuccess()
        close()
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

    private fun clickHelp() {
        binding.tvReturnHelp.setSingleOnClickListener {
            viewModel.updateIsHelp(true)
            showBottomSheet()
        }
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

    private fun scanQrSuccess() {
        viewModel.qrLocation.observe(this) {
            if(viewModel.isHelp.value != true) showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        capture.onPause()
        val bottomSheet = ReturnBottomSheet()

        with(bottomSheet) {
            setStyle(DialogFragment.STYLE_NORMAL, R.style.UmbrellaFriendBottomSheetTheme)
            show(supportFragmentManager, "ReturnBottomSheet")
        }
    }

    private fun setReturnBtnClickListener() {
        supportFragmentManager.setFragmentResultListener(
            "ReturnBottomSheet",
            this
        ) { requestKey, bundle ->
            if (requestKey == "ReturnBottomSheet") {
                val clickDo = bundle.getBoolean("isReturn", false)
                val location = bundle.getString("location", "")

                if (clickDo) {
                    viewModel.checkReturnAvailable(location)
                } else {
                    viewModel.updateIsHelp(false)
                    capture.onResume()
                }
            }
        }
    }

    private fun checkLocationSame() {
        viewModel.isReturnAvailable.observe(this) { isRentalAvailable ->
            if (isRentalAvailable) {
                viewModel.returnUmbrella()
            } else {
                BindingCustomDialog.Builder().build(
                    title = "잠시만요!",
                    subtitle = "선택한 장소와 일치하지 않아요.\n다시 인증해주세요.",
                    btnContent = "확인",
                    imageDrawable = R.drawable.ic_notice,
                    btnDoAction = { capture.onResume() },
                    btnBackAction = {},
                    isBackBtn = false
                ).show(supportFragmentManager, "CUSTOM_DIALOG")
            }
        }
    }

    private fun checkRentSuccess() {
        viewModel.isReturn.observe(this) { isReturn ->
            if (isReturn) {
                startActivity(Intent(this@ReturnActivity, ReturnCompleteActivity::class.java))
                finish()
            } else {
                toast("반납이 되지 않았습니다. 다시 시도해주세요.")
                finish()
            }
        }
    }

    private fun close() {
        binding.btnReturnExit.setOnClickListener {
            finish()
        }
    }

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 1000
    }
}