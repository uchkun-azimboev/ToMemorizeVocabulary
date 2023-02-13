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
    private val photoRepository: PhotoRepository,
    private val wordRepository: WordRepository
) : ViewModel() {

    private var _photos = MutableLiveData<Resource<ResponsePhotos>>()
    val photos: LiveData<Resource<ResponsePhotos>> get() = _photos

    private var _words = MutableLiveData<Resource<List<Word>>>()
    val words: LiveData<Resource<List<Word>>> get() = _words


    fun insertWordToRoom(word: Word) = viewModelScope.launch {
        wordRepository.insert(word)
    }

    fun incrementWordCount(title: String) = viewModelScope.launch {
        wordRepository.incrementWordCount(title)
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

    fun getPhotos(page: Int, query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _photos.postValue(Resource.loading())
                photoRepository.getPhotos(page, query).let {
                    if (it.isSuccessful) {
                        _photos.postValue(Resource.success(it.body()!!))
                    } else {
                        _photos.postValue(Resource.error(it.errorBody().toString()))
                    }
                }
            } catch (e: Exception) {
                _photos.postValue(Resource.error(e.localizedMessage ?: "Error"))
            }
        }
    }
}