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
    protected lateinit var mediaPlayer: MediaPlayer

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Set the language for the TTS engine
            textToSpeech.language = Locale.ENGLISH
        }
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

    private fun setUpMediaPlayer() {
        mediaPlayer = MediaPlayer()
    }

    protected fun setUpTTS() {
        textToSpeech = TextToSpeech(requireActivity(), this)
    }

    protected fun speakText(msg: String) {
        mediaPlayer.stop()
        textToSpeech.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null)
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
}