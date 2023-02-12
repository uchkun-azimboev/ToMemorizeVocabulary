package uz.pdp.tomemorizevocabulary.repository

import uz.pdp.tomemorizevocabulary.model.Word

interface WordRepository {
    suspend fun insert(word: Word)
    suspend fun getWords(category: String): List<Word>
}