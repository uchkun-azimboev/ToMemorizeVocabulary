package uz.pdp.tomemorizevocabulary.ui.edit.word

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.pdp.tomemorizevocabulary.data.local.entity.Word
import uz.pdp.tomemorizevocabulary.repository.WordRepository
import javax.inject.Inject

@HiltViewModel
class EditWordViewModel @Inject constructor(
    private val wordRepository: WordRepository
) : ViewModel() {

    fun updateWord(word: Word) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                wordRepository.updateWord(word)
            }
        }
    }
}