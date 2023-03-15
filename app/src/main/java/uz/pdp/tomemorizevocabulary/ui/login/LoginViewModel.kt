package uz.pdp.tomemorizevocabulary.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.pdp.tomemorizevocabulary.data.local.entity.User
import uz.pdp.tomemorizevocabulary.repository.UserRepository
import uz.pdp.tomemorizevocabulary.utils.Resource
import uz.pdp.tomemorizevocabulary.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private var _login = SingleLiveEvent<Resource<User>>()
    val login: LiveData<Resource<User>> get() = _login

    fun login(user: User) {
        _login.postValue(Resource.loading())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    userRepository.login(user)
                    _login.postValue(Resource.success(user))
                } catch (e: Exception) {
                    _login.postValue(Resource.error(e.localizedMessage))
                }
            }
        }
    }

    fun getState() = userRepository.getState()
}