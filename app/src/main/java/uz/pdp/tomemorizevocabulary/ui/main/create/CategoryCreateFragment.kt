package uz.pdp.tomemorizevocabulary.ui.main.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.imageview.ShapeableImageView
import dagger.hilt.android.AndroidEntryPoint
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.databinding.FragmentCategoryCreateBinding
import uz.pdp.tomemorizevocabulary.data.local.entity.Category
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class CategoryCreateFragment : Fragment() {

    private var _bn: FragmentCategoryCreateBinding? = null
    private val binding get() = _bn!!

    private val createCategoryViewModel: CreateCategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _bn = FragmentCategoryCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = binding.apply {

        ivBack click {
            requireActivity().onBackPressed()
        }

        btnCancel click {
            requireActivity().onBackPressed()
        }

        btnCreate click {
            createCategory()
        }
    }

    private fun createCategory() {
        if (isValidCategory()) {

            createCategoryViewModel.insertCategory(
                Category(
                    title = binding.etTitle.text.toString().trim(),
                    description = binding.etDescription.text.toString().trim(),
                    createDate = getCurrentDate()
                )
            )

            findNavController().navigate(R.id.action_lessonCreateFragment_to_mainFragment)

        } else {
            toast(getString(R.string.str_make_sure))
        }
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun isValidCategory(): Boolean {
        binding.apply {
            if (etTitle.text!!.toString().trim().isEmpty()) return false
            if (etDescription.text!!.toString().trim().isEmpty()) return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bn = null
    }

}