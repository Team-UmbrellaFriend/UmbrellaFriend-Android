package com.sookmyung.umbrellafriend.ui.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.FragmentReportBinding
import com.sookmyung.umbrellafriend.util.binding.BindingFragment
import com.sookmyung.umbrellafriend.util.setSingleOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment : BindingFragment<FragmentReportBinding>(R.layout.fragment_report) {
    private val viewModel by viewModels<ReportViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        report()
        finishReport()
    }

    private fun report() {
        binding.btnReport.setSingleOnClickListener {
            viewModel.postReport()
        }
    }

    private fun finishReport() {
        viewModel.isReport.observe(viewLifecycleOwner) { isReport ->
            if (isReport) {
                val fragmentManager =
                    requireActivity().supportFragmentManager
                fragmentManager.beginTransaction().remove(this).commit()
                fragmentManager.popBackStack()

            }
        }
    }
}