package uz.pdp.tomemorizevocabulary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 *
 * https://www.behance.net/gallery/103934771/English-Learning-App-redesign?tracking_source=search_projects%7Cvocabulary+app
 *
 * https://github.com/LittleMango/StackLayoutManager
 * */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ToMemorizeVocabulary)
        setContentView(R.layout.activity_main)
    }
}