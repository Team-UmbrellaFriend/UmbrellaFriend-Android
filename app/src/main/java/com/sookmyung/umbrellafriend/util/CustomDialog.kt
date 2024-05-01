package com.sookmyung.umbrellafriend.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sookmyung.umbrellafriend.databinding.DialogCustomBinding

class CustomDialog : BindingCustomDialog() {
    private val binding: DialogCustomBinding
        get() = requireNotNull(_binding as DialogCustomBinding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogCustomBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    override fun setImage() {
        imageDrawable?.let { binding.dialogIcon.setImageResource(it) }
    }

    override fun setTitle() {
        binding.dialogTitle.text = title
    }

    override fun setSubtitle() {
        binding.dialogSubtitle.text = subtitle
    }

    override fun setDoBtnClick(action: () -> Unit) {
        binding.btnDialogDo.setSingleOnClickListener { action() }
    }

    override fun setBackBtnClick(action: () -> Unit) {
        binding.btnDialogBack.setSingleOnClickListener { action() }
    }

    override fun setBackBtnVisibility() {
        binding.btnDialogBack.visibility = when(isBackBtn){
            true -> View.VISIBLE
            else -> View.GONE
        }
    }
}