package uz.pdp.tomemorizevocabulary.repository

import uz.pdp.tomemorizevocabulary.data.local.dao.WordDao
import uz.pdp.tomemorizevocabulary.data.local.entity.Word

class WordRepositoryImp(private val wordDao: WordDao) : WordRepository {

    override suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

    override suspend fun getWords(categoryId: Int): List<Word> {
        return wordDao.getWords(categoryId)
    }

    override suspend fun getRandomWords(count: Int): List<Word> {
        return wordDao.getRandomWords(count)
    }

    override suspend fun getLastWords(count: Int): List<Word> {
        return wordDao.getLastWords(count)
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

    override suspend fun deleteWordsById(categoryId: Int) {
        wordDao.deleteWordsById(categoryId)
    }
}