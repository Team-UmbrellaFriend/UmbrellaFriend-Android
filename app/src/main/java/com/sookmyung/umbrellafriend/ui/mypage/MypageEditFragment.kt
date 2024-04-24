package com.sookmyung.umbrellafriend.ui.mypage

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.FragmentMypageEditBinding
import com.sookmyung.umbrellafriend.ui.mypage.MypageViewModel.Companion.USER_ID
import com.sookmyung.umbrellafriend.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MypageEditFragment :
    BindingFragment<FragmentMypageEditBinding>(R.layout.fragment_mypage_edit) {
    private val viewModel by viewModels<MypageEditViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        getUserIdBundle()
        showError()
        checkJoinAvailable()
        edit()
        moveToMain()
    }
    private fun getUserIdBundle() {
        val bundle = arguments
        if (bundle != null) {
            val userId = bundle.getInt(USER_ID)
            viewModel.updateUserId(userId)
        }
    }

    private fun checkJoinAvailable() {
        observeFieldsAndJoinAvailability(viewModel.email, viewModel.name, viewModel.studentId)
    }

    private fun showError() {
        observeTextAndSetError(
            binding.etMypageEditPhone,
            viewModel.phoneNumber
        ) { viewModel.isValidPhoneNumber() }

        observeTextAndSetError(
            binding.etMypageEditPassword,
            viewModel.password
        ) { viewModel.isValidPassword() }

        observeTextAndSetError(
            binding.etMypageEditPasswordCheck,
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
                setNotMatchErrorTextVisibility(isValidText)
            }
            viewModel.isJoinAvailable()
        }
    }

    private fun setNotMatchErrorTextVisibility(isValidText: Boolean) {
        binding.tvMypageEditPasswordNotMatch.visibility =
            if (isValidText) View.GONE else View.VISIBLE
    }

    private fun observeFieldsAndJoinAvailability(vararg liveData: LiveData<String>) {
        liveData.forEach { data ->
            data.observe(viewLifecycleOwner) {
                viewModel.isJoinAvailable()
            }
        }
    }

    private fun edit() {
        binding.btnDone.setOnClickListener {
            viewModel.putProfile()
        }
    }

    private fun moveToMain() {
        viewModel.isEditSuccess.observe(viewLifecycleOwner) { isEditSuccess ->
            if (isEditSuccess) {
                val fragmentManager =
                    requireActivity().supportFragmentManager
                fragmentManager.beginTransaction().remove(this).commit()
                fragmentManager.popBackStack()
            }
        }
    }
}