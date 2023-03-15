package uz.pdp.tomemorizevocabulary.ui.main.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.pdp.tomemorizevocabulary.data.local.entity.Category
import uz.pdp.tomemorizevocabulary.repository.CategoryRepository
import uz.pdp.tomemorizevocabulary.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class CreateCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    fun insertCategory(category: Category) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                categoryRepository.insert(category)
                userRepository.incrementAllCategories(userRepository.getState())
            }
        }
    }
}