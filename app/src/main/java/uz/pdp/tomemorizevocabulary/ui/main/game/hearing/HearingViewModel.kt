package uz.pdp.tomemorizevocabulary.ui.main.game.hearing

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.pdp.tomemorizevocabulary.data.local.entity.Word
import uz.pdp.tomemorizevocabulary.repository.WordRepository
import uz.pdp.tomemorizevocabulary.utils.Resource
import uz.pdp.tomemorizevocabulary.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class HearingViewModel @Inject constructor(
    private val wordRepository: WordRepository
) : ViewModel() {

    private var _randomWords = SingleLiveEvent<Resource<List<Word>>>()
    val randomWords: LiveData<Resource<List<Word>>> get() = _randomWords

    fun getRandomWordsByCategory(categoryId: Int) {
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

    fun getRandomWords(count: Int) {
        _randomWords.postValue(Resource.loading())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = wordRepository.getRandomWords(count)
                    _randomWords.postValue(Resource.success(response))
                } catch (e: Exception) {
                    _randomWords.postValue(Resource.error(e.localizedMessage))
                }
            }
        }
    }

    fun getLastWords(count: Int) {
        _randomWords.postValue(Resource.loading())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = wordRepository.getLastWords(count)
                    _randomWords.postValue(Resource.success(response.shuffled()))
                } catch (e: Exception) {
                    _randomWords.postValue(Resource.error(e.localizedMessage))
                }
            }
        }
    }
}
