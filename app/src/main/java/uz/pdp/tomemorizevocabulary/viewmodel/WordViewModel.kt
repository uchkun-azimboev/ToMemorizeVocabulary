package uz.pdp.tomemorizevocabulary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.pdp.tomemorizevocabulary.data.local.entity.Word
import uz.pdp.tomemorizevocabulary.model.photos.ResponsePhotos
import uz.pdp.tomemorizevocabulary.repository.PhotoRepository
import uz.pdp.tomemorizevocabulary.repository.WordRepository
import uz.pdp.tomemorizevocabulary.utils.Resource
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val wordRepository: WordRepository
) : ViewModel() {


    private var _words = MutableLiveData<Resource<List<Word>>>()
    val words: LiveData<Resource<List<Word>>> get() = _words

    private var _randomWords = MutableLiveData<Resource<List<Word>>>()
    val randomWords: LiveData<Resource<List<Word>>> get() = _randomWords

    private var _searchWords = MutableLiveData<Resource<List<Word>>>()
    val searchWords: LiveData<Resource<List<Word>>> get() = _searchWords

    fun insertWordToRoom(word: Word) = viewModelScope.launch {
        wordRepository.insert(word)
    }

    fun incrementWordCount(title: String) = viewModelScope.launch {
        wordRepository.incrementWordCount(title)
    }

    fun incrementSuccessCount(id: Int) = viewModelScope.launch {
        wordRepository.incrementSuccessCount(id)
    }

    fun incrementAllCount(id: Int) = viewModelScope.launch {
        wordRepository.incrementAllCount(id)
    }

    fun getWords(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _words.postValue(Resource.loading())
            try {
                val response = wordRepository.getWords(category)
                _words.postValue(Resource.success(response))
            } catch (e: Exception) {
                _words.postValue(Resource.error(e.localizedMessage))
            }
        }
    }

    fun getSearchWords(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchWords.postValue(Resource.loading())

            try {
                val response = wordRepository.getSearchWord(text)
                _searchWords.postValue(Resource.success(response))

            } catch (e: Exception) {
                _searchWords.postValue(Resource.error(e.localizedMessage))
            }
        }
    }

    fun getRandomWords(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _randomWords.postValue(Resource.loading())
            try {
                val response = wordRepository.getWords(category)
                _randomWords.postValue(Resource.success(response.shuffled()))
            } catch (e: Exception) {
                _randomWords.postValue(Resource.error(e.localizedMessage))
            }
        }
    }
}