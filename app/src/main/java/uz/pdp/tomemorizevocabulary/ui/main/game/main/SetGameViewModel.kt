package uz.pdp.tomemorizevocabulary.ui.main.game.main

import androidx.lifecycle.LiveData
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
class SetGameViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private var _categories = SingleLiveEvent<Resource<List<Category>>>()
    val categories: LiveData<Resource<List<Category>>> get() = _categories

    fun getAllCategory() {
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
}