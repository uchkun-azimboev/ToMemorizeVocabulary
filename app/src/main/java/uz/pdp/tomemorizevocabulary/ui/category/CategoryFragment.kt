package uz.pdp.tomemorizevocabulary.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.databinding.FragmentCategoryBinding
import uz.pdp.tomemorizevocabulary.model.Word
import uz.pdp.tomemorizevocabulary.ui.word.WordAdapter
import uz.pdp.tomemorizevocabulary.utils.Extensions.click

class CategoryFragment : Fragment() {

    private var _bn: FragmentCategoryBinding? = null
    private val binding get() = _bn!!

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
    }

    private fun initViews() = binding.apply {
        ivBack click { requireActivity().onBackPressed() }
        rvWords.adapter = WordAdapter().apply {
            submitList(getWords())
        }

        frameCreate click {
            findNavController().navigate(R.id.action_lessonFragment_to_addWordFragment)
        }
    }

    fun getWords(): MutableList<Word> {
        val list = ArrayList<Word>()
        for (i in 1..5) {
            list.add(Word("Description", "Tarif, u haqida ma'lumot", null, 10, 13, R.color.blue))
            list.add(Word("Iron man", "Temir odam", R.drawable.image_man, 7, 9, R.color.blue))
            list.add(
                Word(
                    "Hi brother",
                    "Qalesan ahvollaring yaxshimi yaxshi yutibsanmi ishlaring joyidami bola chaqa tinchmi o'qish bo'p turibdimi",
                    null,
                    7,
                    9,
                    R.color.blue
                )
            )
        }
        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bn = null
    }
}