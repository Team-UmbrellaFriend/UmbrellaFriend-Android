package com.sookmyung.umbrellafriend.ui.join

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.fragment.app.viewModels
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.FragmentJoinRegisterPhotoBinding
import com.sookmyung.umbrellafriend.ui.join.ImageCropActivity.Companion.CROP_URI
import com.sookmyung.umbrellafriend.ui.join.ImageCropActivity.Companion.IMAGE_URI
import com.sookmyung.umbrellafriend.util.binding.BindingAdapter.setImage
import com.sookmyung.umbrellafriend.util.binding.BindingFragment

class JoinRegisterPhotoFragment :
    BindingFragment<FragmentJoinRegisterPhotoBinding>(R.layout.fragment_join_register_photo) {
    private val viewModel by viewModels<JoinRegisterPhotoViewModel>()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val imageChooserCallback = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            val loadingIntent = Intent(requireActivity(), ImageCropActivity::class.java)
            loadingIntent.putExtra(IMAGE_URI, uri.toString())
            resultLauncher.launch(loadingIntent)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        startGallery()
        removePhoto()
        getCroppedUri()
        moveToJoinInfo()
    }

    private fun removePhoto() {
        binding.btnJoinRegisterClose.setOnClickListener {
            viewModel.clearUri()
        }
    }

    private fun startGallery() {
        binding.ivJoinRegisterPhotoBackground.setOnClickListener {
            imageChooserCallback.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }
    }

    private fun getCroppedUri() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    viewModel.updateUri(result.data?.getStringExtra(CROP_URI) ?: "")
                    binding.ivJoinRegisterPhotoPreview.setImage(result.data?.getStringExtra(CROP_URI))
                }
            }
    }

    private fun moveToJoinInfo() {
        binding.btnJoinRegisterPhotoNext.setOnClickListener {
            //프래그먼트 전환
            //전환 전에 ml kit 사용해서 결과값 얻어내기
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fcv_join, JoinInfoFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}