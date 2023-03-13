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
import uz.pdp.tomemorizevocabulary.viewmodel.CategoryViewModel
import uz.pdp.tomemorizevocabulary.viewmodel.UserViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class CategoryCreateFragment : Fragment() {

    private var _bn: FragmentCategoryCreateBinding? = null
    private val binding get() = _bn!!

    private lateinit var colorList: ArrayList<ShapeableImageView>
    private var pickColor: Int = R.color.pure_gray

    private val categoryViewModel: CategoryViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

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
        colorList = arrayListOf(ivOrange, ivGreen, ivBlue, ivPureGray)

        ivBack click {
            requireActivity().onBackPressed()
        }

        btnCancel click {
            requireActivity().onBackPressed()
        }

        ivOrange click { chooseColor(ivOrange, R.color.orange) }
        ivBlue click { chooseColor(ivBlue, R.color.blue) }
        ivPureGray click { chooseColor(ivPureGray, R.color.pure_gray) }
        ivGreen click { chooseColor(ivGreen, R.color.green) }

        btnCreate click {
            createCategory()
        }
    }

    private fun createCategory() {
        if (isValidCategory()) {

            categoryViewModel.insertCategory(
                Category(
                    title = binding.etTitle.text.toString().trim(),
                    description = binding.etDescription.text.toString().trim(),
                    createDate = getCurrentDate(),
                    color = pickColor
                )
            )

            userViewModel.incrementAllCategories(userViewModel.getState())

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

    private fun chooseColor(ivColor: ShapeableImageView, color: Int) = binding.apply {
        for (view in colorList)
            view.setStrokeColorResource(R.color.dark)
        ivColor.setStrokeColorResource(R.color.yellow)
        pickColor = color
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bn = null
    }

}