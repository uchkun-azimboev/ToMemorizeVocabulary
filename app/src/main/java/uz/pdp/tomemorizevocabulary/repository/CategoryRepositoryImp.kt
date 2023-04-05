package uz.pdp.tomemorizevocabulary.repository

import uz.pdp.tomemorizevocabulary.data.local.dao.CategoryDao
import uz.pdp.tomemorizevocabulary.data.local.entity.Category

class CategoryRepositoryImp(private val categoryDao: CategoryDao) : CategoryRepository {
    override suspend fun insert(category: Category) {
        categoryDao.insert(category)
    }

    override suspend fun getAllCategory(): List<Category> {
        return categoryDao.getAllCategory()
    }

    override suspend fun getCategoryById(id: Int): Category {
        return categoryDao.getCategoryById(id)
    }

    override suspend fun incrementWordCount(id: Int) {
        categoryDao.incrementWordCount(id)
    }

    override suspend fun incrementColor(id: Int) {
        categoryDao.incrementColor(id)
    }

    override suspend fun setDefaultColor(id: Int) {
        categoryDao.setDefaultColor(id)
    }

    override suspend fun decrementWordCount(id: Int) {
        categoryDao.decrementWordCount(id)
    }

    override suspend fun updateCategory(id: Int, newTitle: String, description: String) {
        categoryDao.updateCategory(id, newTitle, description)
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }
}