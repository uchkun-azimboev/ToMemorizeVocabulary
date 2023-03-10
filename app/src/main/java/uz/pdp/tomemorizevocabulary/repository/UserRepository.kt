package uz.pdp.tomemorizevocabulary.repository

import androidx.room.Query
import uz.pdp.tomemorizevocabulary.data.local.entity.User

interface UserRepository {
    suspend fun login(user: User)
    suspend fun getUser(username: String): User
    suspend fun incrementAllCategories(username: String)
    suspend fun decrementAllCategories(username: String)
    suspend fun incrementCompleted(username: String)
    fun logout()
    fun getState(): String
}