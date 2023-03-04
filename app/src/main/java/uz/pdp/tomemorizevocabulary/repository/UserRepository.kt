package uz.pdp.tomemorizevocabulary.repository

import uz.pdp.tomemorizevocabulary.data.local.entity.User

interface UserRepository {
    suspend fun login(user: User)
    suspend fun getUser(username: String): User
    fun logout()
    fun getState(): String
}