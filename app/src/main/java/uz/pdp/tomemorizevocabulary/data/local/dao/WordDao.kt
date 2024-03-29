package uz.pdp.tomemorizevocabulary.data.local.dao

import androidx.room.*
import uz.pdp.tomemorizevocabulary.data.local.entity.Word

@Dao
interface WordDao {

    @Insert
    suspend fun insert(word: Word)

    @Query("select * from word where category_id = :categoryId order by all_count - success_count desc")
    suspend fun getWords(categoryId: Int): List<Word>

    @Query("select * from word order by RANDOM() limit :count")
    suspend fun getRandomWords(count: Int): List<Word>

    @Query("select * from word order by id desc limit :count")
    suspend fun getLastWords(count: Int): List<Word>


    @Query("update word set success_count = success_count + 1 where id = :id")
    suspend fun incrementSuccessCount(id: Int)

    @Query("update word set all_count = all_count + 1 where id = :id")
    suspend fun incrementALlCount(id: Int)

    @Query("select * from word where word.phrase like :text || '%'")
    suspend fun getSearchWord(text: String): List<Word>

    @Query("delete from word where category_id = :id")
    suspend fun deleteWordsById(id: Int)

    @Update
    suspend fun updateWord(word: Word)

    @Delete
    suspend fun deleteWord(word: Word)
}