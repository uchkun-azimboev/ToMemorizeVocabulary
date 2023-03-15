package uz.pdp.tomemorizevocabulary.ui.category.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.pdp.tomemorizevocabulary.data.local.entity.Word
import uz.pdp.tomemorizevocabulary.model.photos.ResponsePhotos
import uz.pdp.tomemorizevocabulary.repository.CategoryRepository
import uz.pdp.tomemorizevocabulary.repository.PhotoRepository
import uz.pdp.tomemorizevocabulary.repository.WordRepository
import uz.pdp.tomemorizevocabulary.utils.Resource
import uz.pdp.tomemorizevocabulary.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class AddWordViewModel @Inject constructor(
    private val wordRepository: WordRepository,
    private val photoRepository: PhotoRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private var _photos = SingleLiveEvent<Resource<ResponsePhotos>>()
    val photos: LiveData<Resource<ResponsePhotos>> get() = _photos

    fun insertWordToRoom(word: Word, title: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                wordRepository.insert(word)
                categoryRepository.incrementWordCount(title)
            }
        }
    }

    fun getPhotos(page: Int, query: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
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
}