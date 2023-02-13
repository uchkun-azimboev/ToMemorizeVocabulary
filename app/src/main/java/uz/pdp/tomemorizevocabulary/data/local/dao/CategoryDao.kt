package uz.pdp.tomemorizevocabulary.data.local.dao

import androidx.room.*
import uz.pdp.tomemorizevocabulary.data.local.entity.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Query("SELECT * FROM category")
    suspend fun getAllCategory(): List<Category>

}