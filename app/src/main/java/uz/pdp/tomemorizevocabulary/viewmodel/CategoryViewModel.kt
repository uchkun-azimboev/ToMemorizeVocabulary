package uz.pdp.tomemorizevocabulary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.pdp.tomemorizevocabulary.data.local.entity.Category
import uz.pdp.tomemorizevocabulary.repository.CategoryRepository
import uz.pdp.tomemorizevocabulary.utils.Resource
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private var _categories = MutableLiveData<Resource<List<Category>>>()
    val categories: LiveData<Resource<List<Category>>> get() = _categories

    fun insertCategory(category: Category) {
        viewModelScope.launch {
            categoryRepository.insert(category)
        }
    }

    fun getAllCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            _categories.postValue(Resource.loading())

            try {

                val response = categoryRepository.getAllCategory()
                _categories.postValue(Resource.success(response))

            } catch (e: Exception) {
                _categories.postValue(Resource.error(e.localizedMessage))
            }
        }
    }

    fun incrementWordCount(title: String) = viewModelScope.launch {
        categoryRepository.incrementWordCount(title)
    }

    fun decrementWordCount(title: String) = viewModelScope.launch {
        categoryRepository.decrementWordCount(title)
    }
}