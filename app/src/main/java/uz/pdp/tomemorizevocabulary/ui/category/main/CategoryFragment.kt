package uz.pdp.tomemorizevocabulary.ui.category.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.data.local.entity.Word
import uz.pdp.tomemorizevocabulary.databinding.FragmentCategoryBinding
import uz.pdp.tomemorizevocabulary.ui.TTSFragment
import uz.pdp.tomemorizevocabulary.utils.Constants
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.gone
import uz.pdp.tomemorizevocabulary.utils.Extensions.makeDialog
import uz.pdp.tomemorizevocabulary.utils.Extensions.setItemTouchHelper
import uz.pdp.tomemorizevocabulary.utils.Extensions.toast
import uz.pdp.tomemorizevocabulary.utils.Extensions.visible
import uz.pdp.tomemorizevocabulary.utils.Resource.Status

@AndroidEntryPoint
class CategoryFragment : TTSFragment() {

    private var _bn: FragmentCategoryBinding? = null
    private val binding get() = _bn!!

    private val categoryViewModel: CategoryViewModel by viewModels()
    private val wordAdapter = WordAdapter()
    private lateinit var categoryTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryTitle = arguments?.getString(Constants.CATEGORY) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _bn = FragmentCategoryBinding.inflate(inflater, container, false)
        setUpTTS()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observer()
    }

    override fun onStart() {
        super.onStart()
        categoryViewModel.getWords(categoryTitle)
    }

    private fun initViews() = binding.apply {

        tvTitle.text = categoryTitle

        ivBack click { requireActivity().onBackPressed() }
        frameCreate click {
            findNavController().navigate(R.id.action_lessonFragment_to_addWordFragment, arguments)
        }
        frameGame click {
            if (rvWords.adapter!!.itemCount > 0)
                findNavController().navigate(R.id.action_lessonFragment_to_playFragment, arguments)
            else toast(getString(R.string.str_dont_word))
        }


        rvWords.adapter = wordAdapter.apply {
            soundClick = { speakText(it) }
        }
        rvWords.setItemTouchHelper(
            swipeLeft = {
                makeDialog(getString(R.string.str_delete), getString(R.string.str_delete_word)) {
                    deleteWord(it)
                }
            },
            swipeRight = {
                openEditWordFragment(it)
            }
        )
    }

    private fun openEditWordFragment(position: Int) {
        findNavController().navigate(
            R.id.action_lessonFragment_to_editWordFragment,
            Bundle().apply {
                putSerializable(getString(R.string.str_word), wordAdapter.currentList[position])
            })
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
        categoryViewModel.words.observe(viewLifecycleOwner) {
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

    private fun deleteWord(position: Int) {
        categoryViewModel.deleteWord(wordAdapter.currentList[position], categoryTitle)
    }
}