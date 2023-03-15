package uz.pdp.tomemorizevocabulary.ui.main.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.pdp.tomemorizevocabulary.data.local.entity.Category
import uz.pdp.tomemorizevocabulary.data.local.entity.User
import uz.pdp.tomemorizevocabulary.repository.CategoryRepository
import uz.pdp.tomemorizevocabulary.repository.UserRepository
import uz.pdp.tomemorizevocabulary.utils.Resource
import uz.pdp.tomemorizevocabulary.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private var _categories = SingleLiveEvent<Resource<List<Category>>>()
    val categories: LiveData<Resource<List<Category>>> get() = _categories

    private var _user = SingleLiveEvent<Resource<User>>()
    val user: LiveData<Resource<User>> get() = _user

    private fun getAllCategory() {
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

    fun deleteCategory(category: Category, username: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                categoryRepository.deleteCategory(category)
                userRepository.decrementAllCategories(username)
            }
        }.invokeOnCompletion {
            loadData(username)
        }
    }

    private fun getUser(username: String) {
        _user.postValue(Resource.loading())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = userRepository.getUser(username)
                    _user.postValue(Resource.success(response))
                } catch (e: Exception) {
                    _user.postValue(Resource.error(e.localizedMessage))
                }
            }
        }
    }

    fun getState() = userRepository.getState()

    fun loadData(username: String) {
        getAllCategory()
        getUser(username)
    }

    /*fun decrementAllCategories(username: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userRepository.decrementAllCategories(username)
            }
        }
    }*/
}