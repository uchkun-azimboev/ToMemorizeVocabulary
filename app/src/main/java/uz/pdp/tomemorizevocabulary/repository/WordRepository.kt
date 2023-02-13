package uz.pdp.tomemorizevocabulary.repository

import uz.pdp.tomemorizevocabulary.data.local.entity.Word

interface WordRepository {
    suspend fun insert(word: Word)
    suspend fun incrementWordCount(title: String)
    suspend fun getWords(category: String): List<Word>
}