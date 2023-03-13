package uz.pdp.tomemorizevocabulary.repository

import uz.pdp.tomemorizevocabulary.data.local.dao.WordDao
import uz.pdp.tomemorizevocabulary.data.local.entity.Word

class WordRepositoryImp(private val wordDao: WordDao) : WordRepository {

    override suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

    override suspend fun getWords(category: String): List<Word> {
        return wordDao.getWords(category)
    }

    override suspend fun getSearchWord(text: String): List<Word> {
        return wordDao.getSearchWord(text)
    }

    override suspend fun incrementSuccessCount(id: Int) {
        wordDao.incrementSuccessCount(id)
    }

    override suspend fun incrementAllCount(id: Int) {
        wordDao.incrementALlCount(id)
    }

    override suspend fun updateWord(word: Word) {
        wordDao.updateWord(word)
    }

    override suspend fun deleteWord(word: Word) {
        wordDao.deleteWord(word)
    }
}