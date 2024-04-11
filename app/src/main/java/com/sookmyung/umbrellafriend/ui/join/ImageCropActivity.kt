package com.sookmyung.umbrellafriend.ui.join

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.viewModels
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivityImageCropBinding
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import java.io.ByteArrayOutputStream


class ImageCropActivity : BindingActivity<ActivityImageCropBinding>(R.layout.activity_image_crop) {
    private val viewModel by viewModels<ImageCropViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getUri()
        startCropView()
    }

    private fun getUri(){
        val uriString = intent.getStringExtra(IMAGE_URI)
        viewModel.updateUri(Uri.parse(uriString))
    }

    private fun startCropView() {
        viewModel.uri.observe(this){
            binding.cropImageViewImageCrop.setImageUriAsync(viewModel.uri.value)
            cropImage()
        }
    }

    private fun cropImage() {
        binding.btnImageCrop.setOnClickListener {
            sendCropUri()
            finish()
        }
    }

    private fun sendCropUri() {
        val croppedUri = binding.cropImageViewImageCrop.croppedImage?.let { bitmapToUri(it) }
        setResult(RESULT_OK, Intent().putExtra(CROP_URI, croppedUri.toString()))
    }

    private fun bitmapToUri(bitmap: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            this.contentResolver,
            bitmap,
            "image_${System.currentTimeMillis()}.jpg",
            null
        )
        return Uri.parse(path)
    }

    companion object {
        const val IMAGE_URI = "IMAGE_URI"
        const val CROP_URI = "CROP_URI"
    }
}