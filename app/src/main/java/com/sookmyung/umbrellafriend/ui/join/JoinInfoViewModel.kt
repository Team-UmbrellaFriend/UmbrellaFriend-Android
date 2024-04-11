package com.sookmyung.umbrellafriend.ui.join

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sookmyung.umbrellafriend.domain.entity.Student


class JoinInfoViewModel : ViewModel() {
    private val _student: MutableLiveData<Student> = MutableLiveData(Student())
    val student: LiveData<Student> get() = _student

    fun updateStudent(data: Student) {
        _student.value = data
    }
}