package uz.pdp.tomemorizevocabulary.ui.category.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.imageview.ShapeableImageView
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.databinding.FragmentCategoryCreateBinding
import uz.pdp.tomemorizevocabulary.utils.Extensions.click

class CategoryCreateFragment : Fragment() {

    private var _bn: FragmentCategoryCreateBinding? = null
    private val binding get() = _bn!!
    private lateinit var colorList: ArrayList<ShapeableImageView>
    private var pickColor: Int = R.color.pure_gray

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