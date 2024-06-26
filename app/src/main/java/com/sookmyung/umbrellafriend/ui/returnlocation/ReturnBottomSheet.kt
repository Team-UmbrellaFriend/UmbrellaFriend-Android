package com.sookmyung.umbrellafriend.ui.returnlocation

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sookmyung.umbrellafriend.R
import com.sookmyung.umbrellafriend.databinding.FragmentReturnBottomsheetBinding
import com.sookmyung.umbrellafriend.domain.entity.Location
import com.sookmyung.umbrellafriend.util.ItemDecoration
import com.sookmyung.umbrellafriend.util.setSingleOnClickListener

class ReturnBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentReturnBottomsheetBinding? = null
    private val binding get() = requireNotNull(_binding)
    private var selectedLocation: String = ""
    private lateinit var itemDeco: RecyclerView.ItemDecoration

    private val returnLocationListAdapter: ReturnLocationListAdapter?
        get() = binding.rvReturnLocation.adapter as? ReturnLocationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReturnBottomsheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setReturnLocationListAdapter()
        setLocationList()
        clickBtn()
        selectLocation()
    }

    private fun setReturnLocationListAdapter() {
        binding.rvReturnLocation.adapter = ReturnLocationListAdapter()
    }

    private fun setLocationList() {
        itemDeco = ItemDecoration(requireContext())
        binding.rvReturnLocation.addItemDecoration(itemDeco)
        returnLocationListAdapter?.submitList(
            listOf(
                Location(
                    "명신관",
                    "myungshin",
                    Uri.parse("android.resource://com.sookmyung.umbrellafriend/${R.drawable.img_myungsin}"),
                    false
                ),
                Location(
                    "르네상스관",
                    "renaissance",
                    Uri.parse("android.resource://com.sookmyung.umbrellafriend/${R.drawable.img_renaissance}"),
                    false
                ),
                Location(
                    "과학관",
                    "science",
                    Uri.parse("android.resource://com.sookmyung.umbrellafriend/${R.drawable.img_science}"),
                    false
                ),Location(
                    "미술대학",
                    "art",
                    Uri.parse("android.resource://com.sookmyung.umbrellafriend/${R.drawable.img_art}"),
                    false
                ),
            )
        )
    }

    private fun clickBtn() {
        val bundle = Bundle()
        binding.btnBottomSheetBack.setSingleOnClickListener {
            bundle.putBoolean("isReturn", false)
            bundle.putString("location", selectedLocation)
            setFragmentResult("ReturnBottomSheet", bundle)
            dismiss()
        }
        binding.btnBottomSheetDo.setSingleOnClickListener {
            bundle.putBoolean("isReturn", true)
            bundle.putString("location", selectedLocation)
            setFragmentResult("ReturnBottomSheet", bundle)
            dismiss()
        }
    }

    private fun selectLocation() {
        returnLocationListAdapter?.setOnThemeClickListener { location ->
            selectedLocation = location.locationEngName
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val bundle = Bundle()
        bundle.putBoolean("isReturn", false)
        bundle.putString("location", selectedLocation)
        setFragmentResult("ReturnBottomSheet", bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}