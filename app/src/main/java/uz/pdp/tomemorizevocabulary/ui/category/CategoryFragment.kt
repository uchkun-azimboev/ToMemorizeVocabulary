package uz.pdp.tomemorizevocabulary.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.databinding.FragmentCategoryBinding
import uz.pdp.tomemorizevocabulary.data.local.entity.Word
import uz.pdp.tomemorizevocabulary.viewmodel.WordViewModel
import uz.pdp.tomemorizevocabulary.utils.Constants
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.gone
import uz.pdp.tomemorizevocabulary.utils.Extensions.visible
import uz.pdp.tomemorizevocabulary.utils.Resource.Status

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private var _bn: FragmentCategoryBinding? = null
    private val binding get() = _bn!!

    private val wordViewModel: WordViewModel by viewModels()
    private val wordAdapter = WordAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bn = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observer()
    }

    override fun onStart() {
        super.onStart()
        wordViewModel.getWords(arguments?.getString(Constants.CATEGORY) ?: "")
    }

    private fun initViews() = binding.apply {

        rvWords.adapter = wordAdapter

        ivBack click { requireActivity().onBackPressed() }

        frameCreate click {
            findNavController().navigate(R.id.action_lessonFragment_to_addWordFragment, arguments)
        }
    }

    private fun setUpRv(list: List<Word>?) {
        binding.apply {
            progressbar.gone()
            if (list.isNullOrEmpty()) {
                tvNoneInfo.visible()
                wordsLine.gone()
                return

            } else {
                wordsLine.visible()
                tvNoneInfo.gone()
            }

            tvWordCount.text =
                list.size.toString().plus(" ").plus(getString(R.string.str_count_words))
            wordAdapter.submitList(list)
        }
    }

    private fun observer() {
        wordViewModel.words.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressbar.visible()
                }
                Status.SUCCESS -> {
                    setUpRv(it.data)
                }
                Status.ERROR -> {
                    setUpRv(null)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bn = null
    }
}