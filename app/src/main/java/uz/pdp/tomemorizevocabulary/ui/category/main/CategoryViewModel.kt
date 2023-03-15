package uz.pdp.tomemorizevocabulary.ui.category.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.pdp.tomemorizevocabulary.data.local.entity.Word
import uz.pdp.tomemorizevocabulary.repository.CategoryRepository
import uz.pdp.tomemorizevocabulary.repository.WordRepository
import uz.pdp.tomemorizevocabulary.utils.Resource
import uz.pdp.tomemorizevocabulary.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val wordRepository: WordRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private var _words = SingleLiveEvent<Resource<List<Word>>>()
    val words: LiveData<Resource<List<Word>>> get() = _words

    fun getWords(category: String) {
        _words.postValue(Resource.loading())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = wordRepository.getWords(category)
                    _words.postValue(Resource.success(response))
                } catch (e: Exception) {
                    _words.postValue(Resource.error(e.localizedMessage))
                }
            }
        }
    }

    fun deleteWord(word: Word, title: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                wordRepository.deleteWord(word)
                categoryRepository.decrementWordCount(title)
            }
        }.invokeOnCompletion {
            getWords(title)
        }
    }

}