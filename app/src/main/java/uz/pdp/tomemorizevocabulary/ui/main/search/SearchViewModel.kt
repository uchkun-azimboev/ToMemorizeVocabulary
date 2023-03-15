package uz.pdp.tomemorizevocabulary.ui.main.search

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
class SearchViewModel @Inject constructor(
    private val wordRepository: WordRepository
) : ViewModel() {

    private var _searchWords = SingleLiveEvent<Resource<List<Word>>>()
    val searchWords: LiveData<Resource<List<Word>>> get() = _searchWords

    fun getSearchWords(text: String) {
        _searchWords.postValue(Resource.loading())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = wordRepository.getSearchWord(text)
                    _searchWords.postValue(Resource.success(response))

                } catch (e: Exception) {
                    _searchWords.postValue(Resource.error(e.localizedMessage))
                }
            }
        }
    }
}