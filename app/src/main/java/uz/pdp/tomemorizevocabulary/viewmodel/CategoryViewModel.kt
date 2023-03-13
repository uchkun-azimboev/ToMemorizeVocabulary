package uz.pdp.tomemorizevocabulary.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.pdp.tomemorizevocabulary.data.local.entity.Category
import uz.pdp.tomemorizevocabulary.repository.CategoryRepository
import uz.pdp.tomemorizevocabulary.utils.Resource
import uz.pdp.tomemorizevocabulary.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private var _categories = SingleLiveEvent<Resource<List<Category>>>()
    val categories: LiveData<Resource<List<Category>>> get() = _categories

    fun insertCategory(category: Category) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                categoryRepository.insert(category)
            }
        }
    }

    fun getAllCategory() {

        Log.d("TEST_WORK", "getAllCategory")

        _categories.postValue(Resource.loading())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = categoryRepository.getAllCategory()
                    _categories.postValue(Resource.success(response))
                } catch (e: Exception) {
                    _categories.postValue(Resource.error(e.localizedMessage))
                }
            }
        }
    }

    fun incrementWordCount(title: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                categoryRepository.incrementWordCount(title)
            }
        }
    }

    fun decrementWordCount(title: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                categoryRepository.decrementWordCount(title)
            }
        }
    }

    fun updateCategory(title: String, newTitle: String, description: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                categoryRepository.updateCategory(title, newTitle, description)
            }
        }
    }

    fun deleteCategory(category: Category) {

        Log.d("TEST_WORK", "deleteCategory")

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                categoryRepository.deleteCategory(category)
            }
        }
    }
}