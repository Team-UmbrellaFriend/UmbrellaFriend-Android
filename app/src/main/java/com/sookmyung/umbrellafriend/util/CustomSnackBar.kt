package com.sookmyung.umbrellafriend.util


import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.CustomSnackbarBinding

class CustomSnackBar(view: View, private val message: String, anchor: View?) {

    private val context = view.context
    private val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT).apply {
        if (anchor != null) anchorView = anchor
    }
    private val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

    private val binding: CustomSnackbarBinding =
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.custom_snackbar, null, false)

    init {
        initMakeView()
        initSetContent()
    }

    private fun initMakeView() {
        with(snackbarLayout) {
            removeAllViews()
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(binding.root, 0)
        }
    }

    private fun initSetContent() {
        binding.tvSnackbar.text = message
    }

    fun show() {
        snackbar.show()
    }

    fun show(duration: Int) {
        snackbar.duration = duration
        snackbar.show()
    }

    companion object {
        fun make(view: View, message: String, anchor: View?) = CustomSnackBar(view, message, anchor)
    }
}