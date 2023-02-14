package uz.pdp.tomemorizevocabulary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.pdp.tomemorizevocabulary.model.photos.ResponsePhotos
import uz.pdp.tomemorizevocabulary.repository.PhotoRepository
import uz.pdp.tomemorizevocabulary.utils.Resource
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private var _photos = MutableLiveData<Resource<ResponsePhotos>>()
    val photos: LiveData<Resource<ResponsePhotos>> get() = _photos

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