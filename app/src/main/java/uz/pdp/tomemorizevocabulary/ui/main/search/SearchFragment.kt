package uz.pdp.tomemorizevocabulary.ui.main.search

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.data.local.entity.Word
import uz.pdp.tomemorizevocabulary.databinding.FragmentSearchBinding
import uz.pdp.tomemorizevocabulary.ui.TTSFragment
import uz.pdp.tomemorizevocabulary.ui.category.main.WordAdapter
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.gone
import uz.pdp.tomemorizevocabulary.utils.Extensions.hideSoftKeyboard
import uz.pdp.tomemorizevocabulary.utils.Extensions.visible
import uz.pdp.tomemorizevocabulary.utils.Resource

@AndroidEntryPoint
class SearchFragment : TTSFragment() {

    private var _bn: FragmentSearchBinding? = null
    private val bn get() = _bn!!

    private val searchViewModel: SearchViewModel by viewModels()
    private val rvAdapter = WordAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bn = FragmentSearchBinding.inflate(inflater, container, false)
        setUpTTS()

        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observer()
    }

    private fun initViews() = bn.apply {

        rvSearch.adapter = rvAdapter.apply {
            soundClick = { speakText(it) }
        }

        ivBack click { requireActivity().onBackPressed() }

        etSearch.apply {
            addTextChangedListener {
                if (!it.isNullOrEmpty()) {
                    searchViewModel.getSearchWords(it.toString().trim().lowercase())
                } else {
                    setUpRv(null)
                }
            }

            setOnEditorActionListener { textView, i, keyEvent ->
                if (keyEvent != null && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER || i == EditorInfo.IME_ACTION_DONE) {
                    hideSoftKeyboard(textView)
                }
                false
            }
        }
    }


    private fun observer() {
        searchViewModel.searchWords.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    bn.progressbar.visible()
                }
                Resource.Status.SUCCESS -> {
                    setUpRv(it.data)
                }
                Resource.Status.ERROR -> {
                    setUpRv(null)
                }
            }
        }
    }

    private fun setUpRv(list: List<Word>?) {
        bn.apply {
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

            rvAdapter.submitList(list)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }

}