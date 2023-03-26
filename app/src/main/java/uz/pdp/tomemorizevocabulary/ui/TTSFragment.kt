package uz.pdp.tomemorizevocabulary.ui

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import java.util.*

open class TTSFragment : Fragment(), TextToSpeech.OnInitListener {

    private lateinit var textToSpeech: TextToSpeech

    protected fun setUpTTS() {
        textToSpeech = TextToSpeech(requireActivity(), this)
    }

    protected fun speakText(msg: String) {
        textToSpeech.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    override fun onDestroy() {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        textToSpeech.shutdown()
        super.onDestroy()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Set the language for the TTS engine
            textToSpeech.language = Locale.ENGLISH
        }
    }
}