package uz.pdp.tomemorizevocabulary.data.local.dao

import androidx.room.*
import uz.pdp.tomemorizevocabulary.data.local.entity.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Query("SELECT * FROM category")
    suspend fun getAllCategory(): List<Category>

    @Query("update category set word_count = word_count + 1 where id = :id")
    suspend fun incrementWordCount(id: Int)

    @Query("update category set word_count = word_count - 1 where id = :id")
    suspend fun decrementWordCount(id: Int)

    @Query("update category set title = :newTitle, description = :description where id = :id")
    suspend fun updateCategory(id: Int, newTitle: String, description: String)

    @Delete
    suspend fun deleteCategory(category: Category)
}