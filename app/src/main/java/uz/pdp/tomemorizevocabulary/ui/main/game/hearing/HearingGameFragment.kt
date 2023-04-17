package uz.pdp.tomemorizevocabulary.ui.main.game.hearing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.databinding.FragmentHearingGameBinding
import uz.pdp.tomemorizevocabulary.model.GameInfo
import uz.pdp.tomemorizevocabulary.ui.main.game.GameBaseFragment
import uz.pdp.tomemorizevocabulary.utils.Constants
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.getScoreColor
import uz.pdp.tomemorizevocabulary.utils.Extensions.gone
import uz.pdp.tomemorizevocabulary.utils.Extensions.toast
import uz.pdp.tomemorizevocabulary.utils.Extensions.visible
import uz.pdp.tomemorizevocabulary.utils.Utils

class HearingGameFragment : GameBaseFragment() {

    private var _bn: FragmentHearingGameBinding? = null
    private val bn get() = _bn!!

    private val hearingGameAdapter = HearingGameAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _bn = FragmentHearingGameBinding.inflate(inflater, container, false)
        setUpTTS()
        return bn.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        observer(onLoading = {
            bn.progressbar.visible()
        }, onSuccess = {
            bn.progressbar.gone()
            bn.viewPlay.visible()
        }, onError = {
            bn.progressbar.gone()
        })
    }

    override fun onStart() {
        super.onStart()
        setUpGame(requireArguments().getSerializable(Constants.GAME_INFO) as GameInfo)
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }

    private fun initViews() = bn.apply {

        rvGame.layoutManager =
            object : LinearLayoutManager(requireContext(), HORIZONTAL, false) {
                override fun canScrollHorizontally(): Boolean {
                    return true
                }
            }

        rvGame.adapter = hearingGameAdapter

        ivBack click { requireActivity().onBackPressed() }

        hearingGameAdapter.apply {
            ivVoiceClick = {
                speakText(it.phrase)
            }
            btnStopClick = {
                showResult()
            }
            rightAnswer = {
                correctItem++
                playRightSound()
            }
            wrongAnswer = {
                playWrongSound()
            }
            nextWord = {
                tvStats.text = getStats(it)
                rvGame.scrollToPosition(it)
            }
        }

        btnOk click {
            findNavController().navigate(R.id.action_hearingGameFragment_to_mainFragment)
        }

        ivPlay click {
            startGame()
        }

        ivVolume click {
            if (isVolumeOff) {
                setVolumeOn()
                ivVolume.setImageResource(R.drawable.ic_sound_24px)
                toast(getString(R.string.str_sound_on))
            } else {
                setVolumeOff()
                ivVolume.setImageResource(R.drawable.ic_volume_off_24px)
                toast(getString(R.string.str_sound_off))
            }
        }
    }

    private fun startGame() {
        bn.apply {
            viewResult.gone()
            viewStart.gone()
            viewGame.visible()
        }

        hearingGameAdapter.submitList(wordList)
        bn.tvStats.text = getStats(0)
    }

    private fun showResult() {
        val stats: Int = correctItem * 100 / itemCount
        if (stats >= 75) playRightSound()
        else playWrongSound()
        bn.apply {
            viewGame.gone()
            viewResult.visible()
            tvScore.text = stats.toString().plus("%")
            tvScore.setTextColor(getScoreColor(stats))
            tvInfo.text = getString(Utils.getScoreTitle(stats))
            tvInfo.setTextColor(getScoreColor(stats))
        }
    }
}