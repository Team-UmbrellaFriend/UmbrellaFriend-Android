package com.sookmyung.umbrellafriend.ui.join

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.FragmentJoinInfoBinding
import com.sookmyung.umbrellafriend.domain.entity.Student
import com.sookmyung.umbrellafriend.ui.join.JoinRegisterPhotoFragment.Companion.NAME
import com.sookmyung.umbrellafriend.ui.join.JoinRegisterPhotoFragment.Companion.STUDENT_ID
import com.sookmyung.umbrellafriend.util.binding.BindingFragment

class JoinInfoFragment : BindingFragment<FragmentJoinInfoBinding>(R.layout.fragment_join_info) {
    private val viewModel by viewModels<JoinInfoViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel

        getStudentBundle()
    }

    private fun getStudentBundle() {
        val bundle = arguments
        if (bundle != null) {
            val studentId = bundle.getString(STUDENT_ID)
            val name = bundle.getString(NAME)
            viewModel.updateStudent(Student(studentId = studentId ?: "", name = name ?: ""))
        }
    }
}