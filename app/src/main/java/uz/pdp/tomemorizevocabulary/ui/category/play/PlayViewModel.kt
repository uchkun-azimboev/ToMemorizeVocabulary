package uz.pdp.tomemorizevocabulary.ui.category.play

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.pdp.tomemorizevocabulary.data.local.entity.Word
import uz.pdp.tomemorizevocabulary.repository.CategoryRepository
import uz.pdp.tomemorizevocabulary.repository.UserRepository
import uz.pdp.tomemorizevocabulary.repository.WordRepository
import uz.pdp.tomemorizevocabulary.utils.Resource
import uz.pdp.tomemorizevocabulary.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val wordRepository: WordRepository,
    private val categoryRepository: CategoryRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private var _randomWords = SingleLiveEvent<Resource<List<Word>>>()
    val randomWords: LiveData<Resource<List<Word>>> get() = _randomWords

    fun getRandomWords(categoryId: Int) {
        _randomWords.postValue(Resource.loading())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = wordRepository.getWords(categoryId)
                    _randomWords.postValue(Resource.success(response.shuffled()))
                } catch (e: Exception) {
                    _randomWords.postValue(Resource.error(e.localizedMessage))
                }
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

    fun incrementColor(categoryId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                categoryRepository.incrementColor(categoryId)
                val category = async { categoryRepository.getCategoryById(categoryId) }
                if (category.await().color == 4) {
                    userRepository.incrementCompleted(userRepository.getState())
                }
            }
        }
    }
}