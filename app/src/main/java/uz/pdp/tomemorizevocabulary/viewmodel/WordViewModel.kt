package uz.pdp.tomemorizevocabulary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.pdp.tomemorizevocabulary.data.local.entity.Word
import uz.pdp.tomemorizevocabulary.model.photos.ResponsePhotos
import uz.pdp.tomemorizevocabulary.repository.PhotoRepository
import uz.pdp.tomemorizevocabulary.repository.WordRepository
import uz.pdp.tomemorizevocabulary.utils.Resource
import uz.pdp.tomemorizevocabulary.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val wordRepository: WordRepository
) : ViewModel() {


    private var _words = SingleLiveEvent<Resource<List<Word>>>()
    val words: LiveData<Resource<List<Word>>> get() = _words

    private var _randomWords = SingleLiveEvent<Resource<List<Word>>>()
    val randomWords: LiveData<Resource<List<Word>>> get() = _randomWords

    private var _searchWords = SingleLiveEvent<Resource<List<Word>>>()
    val searchWords: LiveData<Resource<List<Word>>> get() = _searchWords

    fun insertWordToRoom(word: Word) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                wordRepository.insert(word)
            }
        }
    }

    fun updateWord(word: Word) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                wordRepository.updateWord(word)
            }
        }
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                wordRepository.deleteWord(word)
            }
        }
    }

    fun incrementSuccessCount(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                wordRepository.incrementSuccessCount(id)
            }
        }
    }

    fun incrementAllCount(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                wordRepository.incrementAllCount(id)
            }
        }
    }

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

    fun getSearchWords(text: String) {
        _searchWords.postValue(Resource.loading())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = wordRepository.getSearchWord(text)
                    _searchWords.postValue(Resource.success(response))

                } catch (e: Exception) {
                    _searchWords.postValue(Resource.error(e.localizedMessage))
                }
            }
        }
    }

    fun getRandomWords(category: String) {
        _randomWords.postValue(Resource.loading())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = wordRepository.getWords(category)
                    _randomWords.postValue(Resource.success(response.shuffled()))
                } catch (e: Exception) {
                    _randomWords.postValue(Resource.error(e.localizedMessage))
                }
            }
        }
    }
}