package uz.pdp.tomemorizevocabulary.repository

import uz.pdp.tomemorizevocabulary.data.local.dao.WordDao
import uz.pdp.tomemorizevocabulary.model.Word

class WordRepositoryImp(private val wordDao: WordDao) : WordRepository {

    override suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

    override suspend fun getWords(category: String): List<Word> {
        return wordDao.getWords(category)
    }
}