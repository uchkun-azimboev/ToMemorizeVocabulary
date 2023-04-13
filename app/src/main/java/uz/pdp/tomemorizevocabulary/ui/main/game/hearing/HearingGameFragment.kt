package uz.pdp.tomemorizevocabulary.ui.main.game.hearing

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.pdp.tomemorizevocabulary.R
import uz.pdp.tomemorizevocabulary.data.local.entity.Word
import uz.pdp.tomemorizevocabulary.databinding.FragmentHearingGameBinding
import uz.pdp.tomemorizevocabulary.model.GameInfo
import uz.pdp.tomemorizevocabulary.ui.TTSFragment
import uz.pdp.tomemorizevocabulary.utils.Constants
import uz.pdp.tomemorizevocabulary.utils.Extensions.click
import uz.pdp.tomemorizevocabulary.utils.Extensions.getScoreColor
import uz.pdp.tomemorizevocabulary.utils.Extensions.gone
import uz.pdp.tomemorizevocabulary.utils.Extensions.visible
import uz.pdp.tomemorizevocabulary.utils.Resource
import uz.pdp.tomemorizevocabulary.utils.Utils


@AndroidEntryPoint
class HearingGameFragment : TTSFragment() {

    private var _bn: FragmentHearingGameBinding? = null
    private val bn get() = _bn!!

    private val hearingViewModel: HearingViewModel by viewModels()
    private val wordList = ArrayList<Word>()
    private var itemCount = 0
    private var correctItem = 0
    private var currentItem = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _bn = FragmentHearingGameBinding.inflate(inflater, container, false)
        setUpTTS()
        return bn.root
    }

    override fun onStart() {
        super.onStart()
        if (itemCount == 0) {
            val gameInfo = requireArguments().getSerializable(Constants.GAME_INFO) as GameInfo
            when (gameInfo.gameType) {
                Constants.IN_CATEGORY -> {
                    hearingViewModel.getRandomWordsByCategory(gameInfo.categoryId)
                }
                Constants.ALL_WORDS -> {
                    hearingViewModel.getRandomWords(gameInfo.wordCount)
                }
                Constants.LAST_WORDS -> {
                    hearingViewModel.getLastWords(gameInfo.wordCount)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observer()
    }

    private fun initViews() = bn.apply {
        ivBack click { requireActivity().onBackPressed() }

        ivVoice click {
            speakText(wordList[currentItem].phrase)
        }

        btnCheck click {
            if (!etAnswer.text.isNullOrEmpty()) {
                val text = etAnswer.text.toString()
                openAnswer(text)
            }
        }

        btnNotKnow click {
            openAnswer("")
        }

        btnStop click {
            showResult()
        }

        btnNext click {
            nextWord()
        }

        btnOk click {
            findNavController().navigate(R.id.action_hearingGameFragment_to_mainFragment)
        }

        ivPlay click {
            startGame()
        }

        ivVolume click {
            if (isVolumeOff) {
                setVolumeUp()
                ivVolume.setImageResource(R.drawable.ic_volume_off_24px)
            } else {
                setVolumeOff()
                ivVolume.setImageResource(R.drawable.ic_sound_24px)
            }
        }
    }

    private fun startGame() {
        bn.apply {
            viewResult.gone()
            viewStart.gone()
            viewGame.visible()
        }
        nextWord()
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

    private fun openAnswer(text: String) {
        val answer = wordList[currentItem].phrase
        bn.apply {
            frameQuestion.gone()
            frameAnswer.visible()

            if (answer == text) {
                correctItem++
                playRightSound()
                tvRight.visible()
                tvWrong.gone()
            } else {
                playWrongSound()
                tvRight.gone()
                tvWrong.visible()
            }

            tvCorrect.text = answer.plus("\n(${wordList[currentItem].meaning})")
            tvYourAnswer.text = text
        }
    }

    private fun nextWord() {
        currentItem++
        if (currentItem < itemCount) {
            bn.apply {
                bn.tvStats.text = getStats()
                etAnswer.text = null
                frameQuestion.visible()
                frameAnswer.gone()
            }
            speakText(wordList[currentItem].phrase)
        } else {
            showResult()
        }
    }

    private fun observer() {
        hearingViewModel.randomWords.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    bn.progressbar.visible()
                }
                Resource.Status.SUCCESS -> {
                    itemCount = it.data?.size ?: 0
                    wordList.addAll(it.data ?: arrayListOf())
                    bn.progressbar.gone()
                    bn.viewPlay.visible()
                }
                Resource.Status.ERROR -> {
                    bn.progressbar.gone()
                }
            }
        }
    }

    private fun getStats(a: Int = currentItem + 1, b: Int = itemCount): String {
        return "$a/$b"
    }

    override fun onDestroy() {
        super.onDestroy()
        _bn = null
    }
}