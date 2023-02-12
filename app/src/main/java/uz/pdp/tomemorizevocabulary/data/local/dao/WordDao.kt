package uz.pdp.tomemorizevocabulary.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import uz.pdp.tomemorizevocabulary.model.Category
import uz.pdp.tomemorizevocabulary.model.Word

@Dao
interface WordDao {
    @Insert
    suspend fun insert(word: Word)

    @Query("select * from word where category = :category")
    suspend fun getWords(category: String): List<Word>
}