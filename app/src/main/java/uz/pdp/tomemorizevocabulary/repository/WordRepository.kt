package uz.pdp.tomemorizevocabulary.repository

import uz.pdp.tomemorizevocabulary.data.local.entity.Word

interface WordRepository {
    suspend fun insert(word: Word)
    suspend fun getWords(categoryId: Int): List<Word>
    suspend fun getRandomWords(count: Int): List<Word>
    suspend fun getLastWords(count: Int): List<Word>
    suspend fun getSearchWord(text: String): List<Word>
    suspend fun incrementSuccessCount(id: Int)
    suspend fun incrementAllCount(id: Int)
    suspend fun updateWord(word: Word)
    suspend fun deleteWord(word: Word)
    suspend fun deleteWordsById(categoryId: Int)
}