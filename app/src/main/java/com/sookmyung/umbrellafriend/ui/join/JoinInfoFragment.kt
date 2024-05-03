package com.sookmyung.umbrellafriend.ui.join

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.FragmentJoinInfoBinding
import com.sookmyung.umbrellafriend.ui.join.JoinRegisterPhotoFragment.Companion.NAME
import com.sookmyung.umbrellafriend.ui.join.JoinRegisterPhotoFragment.Companion.STUDENT_ID
import com.sookmyung.umbrellafriend.ui.join.JoinRegisterPhotoFragment.Companion.URI
import com.sookmyung.umbrellafriend.ui.splash.SplashEntryActivity
import com.sookmyung.umbrellafriend.util.ContentUriRequestBody
import com.sookmyung.umbrellafriend.util.binding.BindingFragment
import com.sookmyung.umbrellafriend.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JoinInfoFragment : BindingFragment<FragmentJoinInfoBinding>(R.layout.fragment_join_info) {
    private val viewModel by viewModels<JoinInfoViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        setHideKeyBoard()
        getStudentBundle()
        showError()
        checkJoinAvailable()
        join()
        moveToMain()
    }

    private fun setHideKeyBoard() {
        val hideKeyBoardViewList = listOf(
            binding.root,
            binding.clJoinInfo
        )

        hideKeyBoardViewList.map {
            it.setOnClickListener { hideKeyboard() }
        }
    }

    private fun getStudentBundle() {
        val bundle = arguments
        if (bundle != null) {
            val studentId = bundle.getString(STUDENT_ID)
            val name = bundle.getString(NAME)
            val uri = bundle.getString(URI)
            val imageMultipartBody =
                ContentUriRequestBody(
                    requireContext(),
                    "profile.studentCard",
                    Uri.parse(uri)
                ).toFormData()
            viewModel.updateStudentCardInfo(name ?: "", studentId ?: "", imageMultipartBody)
        }
    }

    private fun checkJoinAvailable() {
        observeFieldsAndJoinAvailability(viewModel.email, viewModel.name, viewModel.studentId)
    }

    private fun showError() {
        observeTextAndSetError(
            binding.etJoinInfoPhone,
            viewModel.phoneNumber
        ) { viewModel.isValidPhoneNumber() }

        observeTextAndSetError(
            binding.etJoinInfoPassword,
            viewModel.password
        ) { viewModel.isValidPassword() }

        observeTextAndSetError(
            binding.etJoinInfoPasswordCheck,
            viewModel.password2
        ) { viewModel.isValidPassword2() }
    }

    private fun observeTextAndSetError(
        editText: EditText,
        liveData: LiveData<String>,
        isValid: () -> Boolean
    ) {
        liveData.observe(viewLifecycleOwner) { text ->
            with(editText) {
                val isValidText = isValid() || text.isNullOrEmpty()
                val backgroundResource =
                    if (isValidText) R.drawable.shape_gray100_fill_12_rect else R.drawable.shape_gray100_fill_error_stroke_12_rect
                val textColorResource = if (isValidText) R.color.gray_1100 else R.color.error

                setBackgroundResource(backgroundResource)
                setTextColor(ContextCompat.getColor(context, textColorResource))
            }
            viewModel.isJoinAvailable()
        }
    }

    private fun observeFieldsAndJoinAvailability(vararg liveData: LiveData<String>) {
        liveData.forEach { data ->
            data.observe(viewLifecycleOwner) {
                viewModel.isJoinAvailable()
            }
        }
    }

    private fun join() {
        binding.btnNext.setOnClickListener {
            viewModel.postJoin()
        }
    }

    private fun moveToMain() {
        viewModel.isJoinSuccess.observe(viewLifecycleOwner) { isJoinSuccess ->
            if (isJoinSuccess) {
                startActivity(
                    Intent(
                        requireActivity(),
                        SplashEntryActivity::class.java
                    ).apply {
                        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    })
            }
        }
    }
}