package uz.pdp.tomemorizevocabulary.ui.main.game

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.pdp.tomemorizevocabulary.data.local.entity.Word
import uz.pdp.tomemorizevocabulary.model.GameInfo
import uz.pdp.tomemorizevocabulary.ui.TTSFragment
import uz.pdp.tomemorizevocabulary.utils.Constants
import uz.pdp.tomemorizevocabulary.utils.Resource

@AndroidEntryPoint
open class GameBaseFragment : TTSFragment() {

    protected var isVolumeOff = false

    private val gameViewModel: GameViewModel by viewModels()
    protected val wordList = ArrayList<Word>()
    protected var itemCount = 0
    protected var correctItem = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setVolumeOn()
    }

    protected fun setVolumeOn() {
        isVolumeOff = false
        val audioManager = requireContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        mediaPlayer.setVolume(maxVolume.toFloat() * 0.25f, maxVolume.toFloat() * 0.25f)
    }

    protected fun setVolumeOff() {
        isVolumeOff = true
        mediaPlayer.setVolume(0f, 0f)
    }

    protected fun setUpGame(gameInfo: GameInfo) {
        if (itemCount == 0) {
            when (gameInfo.gameType) {
                Constants.IN_CATEGORY -> {
                    gameViewModel.getRandomWordsByCategory(gameInfo.categoryId)
                }

                Constants.ALL_WORDS -> {
                    gameViewModel.getRandomWords(gameInfo.wordCount)
                }

                Constants.LAST_WORDS -> {
                    gameViewModel.getLastWords(gameInfo.wordCount)
                }
            }
        }
    }

    protected fun observer(
        onLoading: () -> Unit,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        gameViewModel.randomWords.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    onLoading.invoke()
                }

                Resource.Status.SUCCESS -> {
                    itemCount = it.data?.size ?: 0
                    wordList.addAll(it.data ?: arrayListOf())
                    onSuccess.invoke()
                }

                Resource.Status.ERROR -> {
                    onError.invoke()
                }
            }
        }
    }

    protected fun getStats(a: Int, b: Int = itemCount): String {
        return "${a + 1}/$b"
    }
}