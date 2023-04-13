package uz.pdp.tomemorizevocabulary.ui

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.pdp.tomemorizevocabulary.R
import java.util.*

open class TTSFragment : Fragment(), TextToSpeech.OnInitListener {

    private lateinit var textToSpeech: TextToSpeech
    private lateinit var mediaPlayer: MediaPlayer
    protected var isVolumeOff = false

    protected fun setUpTTS() {
        textToSpeech = TextToSpeech(requireActivity(), this)
    }

    private fun setUpMediaPlayer() {
        mediaPlayer = MediaPlayer()
        setVolumeUp()
    }

    protected fun speakText(msg: String) {
        mediaPlayer.stop()
        textToSpeech.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    protected fun setVolumeUp() {
        isVolumeOff = false
        val audioManager = requireContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        mediaPlayer.setVolume(maxVolume.toFloat() * 0.25f, maxVolume.toFloat() * 0.25f)
    }

    protected fun setVolumeOff() {
        isVolumeOff = true
        mediaPlayer.setVolume(0f, 0f)
    }

    protected fun playRightSound() {
        mediaPlayer.stop()
        mediaPlayer.reset()
        mediaPlayer.setDataSource(
            requireContext(),
            Uri.parse("android.resource://" + requireContext().packageName + "/" + R.raw.sound_success)
        )
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    protected fun playWrongSound() {
        mediaPlayer.stop()
        mediaPlayer.reset()
        mediaPlayer.setDataSource(
            requireContext(),
            Uri.parse("android.resource://" + requireContext().packageName + "/" + R.raw.sound_fail)
        )
        mediaPlayer.prepare()
        mediaPlayer.start()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMediaPlayer()
    }

    override fun onDestroy() {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        textToSpeech.shutdown()
        mediaPlayer.stop()
        super.onDestroy()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Set the language for the TTS engine
            textToSpeech.language = Locale.ENGLISH
        }
    }
}