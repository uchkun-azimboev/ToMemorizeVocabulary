package uz.pdp.tomemorizevocabulary.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import uz.pdp.tomemorizevocabulary.data.local.entity.Word

@Dao
interface WordDao {

    @Insert
    suspend fun insert(word: Word)

    @Query("select * from word where category_title = :title")
    suspend fun getWords(title: String): List<Word>

    @Query("update category set word_count = word_count + 1 where title = :title")
    suspend fun incrementWordCount(title: String)

    @Query("select * from word where word.phrase like :text || '%'")
    suspend fun getSearchWord(text: String): List<Word>
}