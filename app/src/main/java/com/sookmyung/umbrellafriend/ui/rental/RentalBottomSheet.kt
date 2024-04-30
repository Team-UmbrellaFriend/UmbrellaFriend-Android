package com.sookmyung.umbrellafriend.ui.rental

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sookmyung.umbrellafriend.databinding.FragmentRentalBottomsheetBinding
import com.sookmyung.umbrellafriend.domain.entity.UmbrellaRental
import com.sookmyung.umbrellafriend.util.setSingleOnClickListener

class RentalBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentRentalBottomsheetBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var rentalInfo: UmbrellaRental

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRentalBottomsheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUmbrellaNum()
        setStudentName()
        setStudentId()
        setDate()
        clickBtn()
    }

    fun setUmbrellaInfo(umbrellaRental: UmbrellaRental) {
        rentalInfo = umbrellaRental
    }

    private fun setUmbrellaNum() {
        binding.tvRentalBottomsheetUmbrellaNum.text =
            "우산 번호 ${rentalInfo.umbrellaNum.toString().padStart(2, '0')}"
    }

    private fun setStudentName() {
        binding.tvRentalBottomsheetName.text = rentalInfo.username
    }

    private fun setStudentId() {
        binding.tvRentalBottomsheetStudentId.text = rentalInfo.studentID.toString()
    }

    private fun setDate() {
        binding.tvRentalBottomsheetDate.text = rentalInfo.date
    }

    private fun clickBtn(){
        binding.btnBottomSheetBack.setSingleOnClickListener {
            dismiss()
        }
        binding.btnBottomSheetDo.setSingleOnClickListener {
            setFragmentResult(
                "RentalBottomSheet", bundleOf("isRental" to true)
            )
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}