package uz.pdp.tomemorizevocabulary.ui.main.game.speech

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.databinding.FragmentSpeechGameBinding
import uz.pdp.tomemorizevocabulary.utils.Extensions.click


class SpeechGameFragment : Fragment() {

    private var _bn: FragmentSpeechGameBinding? = null
    private val bn get() = _bn!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bn = FragmentSpeechGameBinding.inflate(inflater, container, false)
        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = bn.apply {
        ivBack click { requireActivity().onBackPressed() }

    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }

}