package uz.pdp.tomemorizevocabulary.ui.word

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.databinding.FragmentAddWordBinding
import uz.pdp.tomemorizevocabulary.utils.Resource


@AndroidEntryPoint
class AddWordFragment : Fragment() {

    private val tag = "AddWordFragment"

    private var _bn: FragmentAddWordBinding? = null
    private val bn get() = _bn!!

    private val viewModel: AddWordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bn = FragmentAddWordBinding.inflate(inflater, container, false)
        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observer()
    }

    private fun initViews() = bn.apply {

        viewModel.getPhotos(1, "cloud")

    }

    private fun observer() {
        viewModel.photos.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {

                }
                Resource.Status.SUCCESS -> {
                    Log.d(tag, "${it.data}")
                }
                Resource.Status.ERROR -> {
                    Log.d(tag, "${it.message}")
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }
}