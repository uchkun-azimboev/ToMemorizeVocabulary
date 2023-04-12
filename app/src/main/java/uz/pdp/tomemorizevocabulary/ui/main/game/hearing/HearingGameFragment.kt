package uz.pdp.tomemorizevocabulary.ui.main.game.hearing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.databinding.FragmentGrammarGameBinding
import uz.pdp.tomemorizevocabulary.databinding.FragmentHearingGameBinding
import uz.pdp.tomemorizevocabulary.ui.TTSFragment
import uz.pdp.tomemorizevocabulary.utils.Extensions.click


@AndroidEntryPoint
class HearingGameFragment : TTSFragment() {

    private var _bn: FragmentHearingGameBinding? = null
    private val bn get() = _bn!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bn = FragmentHearingGameBinding.inflate(inflater, container, false)
        setUpTTS()
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