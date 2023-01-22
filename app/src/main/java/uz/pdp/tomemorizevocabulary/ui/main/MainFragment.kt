package uz.pdp.tomemorizevocabulary.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.databinding.FragmentMainBinding
import uz.pdp.tomemorizevocabulary.model.Lesson
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import java.util.Random

class MainFragment : Fragment() {

    private var _bn: FragmentMainBinding? = null
    private val binding get() = _bn!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _bn = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = binding.apply {
        rvLessons.adapter = LessonAdapter {
            findNavController().navigate(R.id.action_mainFragment_to_lessonFragment)
        }.apply {
            submitList(getLessons())
        }

        frameCreate.click {
            findNavController().navigate(R.id.action_mainFragment_to_lessonCreateFragment)
        }
    }

    private fun getLessons(): MutableList<Lesson> {
        val lessons = ArrayList<Lesson>()
        for (i in 1..20) {
            lessons.add(
                Lesson(
                    title = "Lesson $i",
                    description = getString(R.string.str_none_lessons_info),
                    wordCount = 20,
                    createDate = "29.12.2022",
                    color = when (Random().nextInt().mod(4)) {
                        1 -> R.color.blue
                        2 -> R.color.orange
                        3 -> R.color.green
                        else -> R.color.pure_gray
                    }
                )
            )
        }
        return lessons
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bn = null
    }
}