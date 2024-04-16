package com.sookmyung.umbrellafriend.ui.join

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.ActivityImageCropBinding
import com.sookmyung.umbrellafriend.util.binding.BindingActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.ByteArrayOutputStream

class ImageCropViewModel : ViewModel(){
    private val _uri: MutableLiveData<Uri> = MutableLiveData(Uri.parse(""))
    val uri: LiveData<Uri> get() = _uri

    fun updateUri(data: Uri){
        _uri.value = data
    }
}