package com.sookmyung.umbrellafriend.util

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BindingCustomDialog : DialogFragment() {
    protected var _binding: ViewBinding? = null
    protected var imageDrawable: Int? = null
    protected var title: String? = null
    protected var subtitle: String? = null
    protected var btnContent: String? = null
    protected var isBackBtn: Boolean? = null
    protected lateinit var btnDoAction: () -> Unit
    protected lateinit var btnBackAction: () -> Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setImage()
        setTitle()
        setSubtitle()
        setBackBtnVisibility()
        setDoBtnClick { btnDoAction() }
        setBackBtnClick { btnBackAction() }
    }

    abstract fun setImage()
    abstract fun setTitle()
    abstract fun setSubtitle()
    abstract fun setDoBtnClick(action: () -> Unit)
    abstract fun setBackBtnClick(action: () -> Unit)
    abstract fun setBackBtnVisibility()

    class Builder() {
        fun build(
            imageDrawable: Int,
            title: String,
            subtitle: String,
            btnContent: String,
            btnDoAction: () -> Unit,
            btnBackAction: () -> Unit,
            isBackBtn: Boolean
        ): BindingCustomDialog {
            val dialog = CustomDialog()
            return dialog.apply {
                this.title = title
                this.subtitle = subtitle
                this.btnContent = btnContent
                this.imageDrawable = imageDrawable
                this.isBackBtn = isBackBtn
                this.btnDoAction = btnDoAction
                this.btnBackAction = btnBackAction
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}