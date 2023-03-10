package uz.pdp.tomemorizevocabulary.repository

import uz.pdp.tomemorizevocabulary.data.local.entity.Word

interface WordRepository {
    suspend fun insert(word: Word)
    suspend fun getWords(category: String): List<Word>
    suspend fun getSearchWord(text: String): List<Word>
    suspend fun incrementSuccessCount(id: Int)
    suspend fun incrementAllCount(id: Int)
    suspend fun updateWord(word: Word)
    suspend fun deleteWord(word: Word)
}