package uz.pdp.tomemorizevocabulary.repository

import android.content.SharedPreferences
import uz.pdp.tomemorizevocabulary.data.local.dao.UserDao
import uz.pdp.tomemorizevocabulary.data.local.entity.User
import uz.pdp.tomemorizevocabulary.utils.Constants.USER_NONE
import uz.pdp.tomemorizevocabulary.utils.Constants.USER_STATE
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val userDao: UserDao, private val sharedPreferences: SharedPreferences
) : UserRepository {

    override suspend fun login(user: User) {
        sharedPreferences.edit().putString(USER_STATE, user.username).apply()
        userDao.insert(user)
    }

    override suspend fun getUser(username: String): User {
        return userDao.getUser(username)
    }

    override suspend fun incrementAllCategories(username: String) {
        userDao.incrementAllCategories(username)
    }

    override suspend fun decrementAllCategories(username: String) {
        userDao.decrementAllCategories(username)
    }

    override suspend fun incrementCompleted(username: String) {
        userDao.incrementCompleted(username)
    }

    override fun logout() {
        sharedPreferences.edit().putString(USER_STATE, USER_NONE).apply()
    }

    override fun getState(): String {
        return sharedPreferences.getString(USER_STATE, USER_NONE)!!
    }
}