package uz.pdp.tomemorizevocabulary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.pdp.tomemorizevocabulary.data.local.entity.User
import uz.pdp.tomemorizevocabulary.repository.UserRepository
import uz.pdp.tomemorizevocabulary.utils.Resource
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private var _login = MutableLiveData<Resource<User>>()
    val login: LiveData<Resource<User>> get() = _login

    private var _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>> get() = _user

    fun login(user: User) {
        _login.postValue(Resource.loading())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepository.login(user)
                _login.postValue(Resource.success(user))
            } catch (e: Exception) {
                _login.postValue(Resource.error(e.localizedMessage))
            }
        }
    }

    fun getUser(username: String) {
        _user.postValue(Resource.loading())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = userRepository.getUser(username)
                _user.postValue(Resource.success(response))
            } catch (e: Exception) {
                _user.postValue(Resource.error(e.localizedMessage))
            }
        }
    }

    fun logout() = userRepository.logout()
    fun getState() = userRepository.getState()
}