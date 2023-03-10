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

    @Query("update user set all_categories = all_categories + 1 where username = :username")
    suspend fun incrementAllCategories(username: String)

    @Query("update user set all_categories = all_categories - 1 where username = :username")
    suspend fun decrementAllCategories(username: String)

    @Query("update user set completed = completed + 1 where username = :username")
    suspend fun incrementCompleted(username: String)
}