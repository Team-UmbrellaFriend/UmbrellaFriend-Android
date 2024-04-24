package com.sookmyung.umbrellafriend.util

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BindingCustomDialog:DialogFragment() {
    protected var _binding: ViewBinding? = null
    protected var imageDrawable: Int? = null
    protected var title: String? = null
    protected var subtitle: String? = null
    protected var btnContent: String? = null
    protected lateinit var btnAction: () -> Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setImage()
        setTitle()
        setSubtitle()
        setBtnClick { btnAction(); dismiss() }
    }

    abstract fun setImage()
    abstract fun setTitle()
    abstract fun setSubtitle()
    abstract fun setBtnClick(action: () -> Unit)

    class Builder() {
        fun build(
            imageDrawable: Int,
            title: String,
            subtitle: String,
            btnContent: String,
            btnAction: () -> Unit
        ): BindingCustomDialog {
            val dialog = CustomDialog()
            return dialog.apply {
                this.title = title
                this.subtitle = subtitle
                this.btnContent = btnContent
                this.imageDrawable = imageDrawable
                this.btnAction = btnAction
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}