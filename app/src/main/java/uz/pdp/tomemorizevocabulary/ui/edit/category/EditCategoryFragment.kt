package uz.pdp.tomemorizevocabulary.ui.edit.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.data.local.entity.Category
import uz.pdp.tomemorizevocabulary.databinding.FragmentEditCategoryBinding
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.toast

@AndroidEntryPoint
class EditCategoryFragment : Fragment() {

    private var _bn: FragmentEditCategoryBinding? = null
    private val bn get() = _bn!!

    private val editCategoryViewModel: EditCategoryViewModel by viewModels()
    private lateinit var theCategory: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        theCategory = arguments?.getSerializable(getString(R.string.str_category)) as Category
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bn = FragmentEditCategoryBinding.inflate(inflater, container, false)
        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = bn.apply {

        etTitle.setText(theCategory.title)
        etDescription.setText(theCategory.description)

        btnCreate click { updateCategory() }
        btnCancel click { requireActivity().onBackPressed() }
        ivBack click { requireActivity().onBackPressed() }
    }

    private fun updateCategory() {
        if (isValidCategory()) {

            editCategoryViewModel.updateCategory(
                theCategory.title,
                bn.etTitle.text.toString().trim(),
                bn.etDescription.text.toString().trim()
            )

            findNavController().navigate(R.id.action_editCategoryFragment_to_mainFragment)

        } else {
            toast(getString(R.string.str_make_sure))
        }
    }

    private fun isValidCategory(): Boolean {
        bn.apply {
            if (etTitle.text!!.toString().trim().isEmpty()) return false
            if (etDescription.text!!.toString().trim().isEmpty()) return false
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }
}