package uz.pdp.tomemorizevocabulary.ui.main.game.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.databinding.FragmentGameBinding
import uz.pdp.tomemorizevocabulary.utils.Constants
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.toast

class GameFragment : BottomSheetDialogFragment() {

    private var _bn: FragmentGameBinding? = null
    private val bn get() = _bn!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bn = FragmentGameBinding.inflate(inflater, container, false)
        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = bn.apply {
        frameGrammar click {
            openSetGameFragment(Constants.GAME_GRAMMAR)
        }

        frameHearing click {
            openSetGameFragment(Constants.GAME_HEARING)
        }

        frameShuffle click {
            openSetGameFragment(Constants.GAME_SHUFFLE)
        }

        frameSpeech click {
            openSetGameFragment(Constants.GAME_SPEECH)
        }

        frameTest click {
            openSetGameFragment(Constants.GAME_TEST)
        }

        frameTyping click {
            openSetGameFragment(Constants.GAME_TYPING)
        }
    }

    private fun openSetGameFragment(gameType: String) {
        findNavController().navigate(R.id.action_gameFragment_to_setGameFragment, Bundle().apply {
            putString(Constants.GAME_TYPE, gameType)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }
}