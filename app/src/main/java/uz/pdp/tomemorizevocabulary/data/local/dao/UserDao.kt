package uz.pdp.tomemorizevocabulary.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import uz.pdp.tomemorizevocabulary.data.local.entity.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Query("select * from user where username = :username")
    suspend fun getUser(username: String): User
}