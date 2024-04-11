package com.sookmyung.umbrellafriend.ui.join

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.fragment.app.viewModels
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.FragmentJoinRegisterPhotoBinding
import com.sookmyung.umbrellafriend.ui.join.ImageCropActivity.Companion.CROP_URI
import com.sookmyung.umbrellafriend.ui.join.ImageCropActivity.Companion.IMAGE_URI
import com.sookmyung.umbrellafriend.util.binding.BindingAdapter.setImage
import com.sookmyung.umbrellafriend.util.binding.BindingFragment
import java.io.IOException

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
            extractNameStudentId(Uri.parse(viewModel.croppedUri.value))
            viewModel.isExtractFinish.observe(viewLifecycleOwner) { finish ->
                if (finish) {
                    val bundle = Bundle().apply {
                        putString(STUDENT_ID, viewModel.studentId.value)
                        putString(NAME, viewModel.name.value)
                    }

                    val joinInfoFragment = JoinInfoFragment()
                    joinInfoFragment.arguments = bundle

                    val fragmentTransaction =
                        requireActivity().supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fcv_join, joinInfoFragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
            }
        }
    }

    private fun extractNameStudentId(img: Uri) {
        try {
            val image = InputImage.fromFilePath(requireContext(), img)
            val recognizer =
                TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())
            recognizer.process(image)
                .addOnSuccessListener {
                    viewModel.extractNumberAndName(it.text)
                    Log.e(MLKIT, "success: ${it.text}")
                }
                .addOnFailureListener {
                    viewModel.extractNumberAndName("")
                    Log.e(MLKIT, "failure: ${it.message}")
                }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        const val MLKIT = "MLKit"
        const val NAME = "NAME"
        const val STUDENT_ID = "STUDENT_ID"
    }
}