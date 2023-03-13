package uz.pdp.tomemorizevocabulary.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.pdp.tomemorizevocabulary.data.local.entity.User
import uz.pdp.tomemorizevocabulary.repository.UserRepository
import uz.pdp.tomemorizevocabulary.utils.Resource
import uz.pdp.tomemorizevocabulary.utils.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private var _login = SingleLiveEvent<Resource<User>>()
    val login: LiveData<Resource<User>> get() = _login

    private var _user = SingleLiveEvent<Resource<User>>()
    val user: LiveData<Resource<User>> get() = _user

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

    fun getUser(username: String) {

        Log.d("TEST_WORK", "getUser")

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

    fun incrementAllCategories(username: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userRepository.incrementAllCategories(username)
            }
        }
    }

    fun decrementAllCategories(username: String) {

        Log.d("TEST_WORK", "decrementAllCategories")

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userRepository.decrementAllCategories(username)
            }
        }
    }

    fun incrementCompleted(username: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userRepository.incrementCompleted(username)
            }
        }
    }

    fun logout() = userRepository.logout()
    fun getState() = userRepository.getState()
}