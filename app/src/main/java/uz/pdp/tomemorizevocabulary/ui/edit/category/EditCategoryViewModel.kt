package uz.pdp.tomemorizevocabulary.ui.edit.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.pdp.tomemorizevocabulary.repository.CategoryRepository
import javax.inject.Inject

@HiltViewModel
class EditCategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    fun updateCategory(id: Int, newTitle: String, description: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                categoryRepository.updateCategory(id, newTitle, description)
            }
        }
    }
}