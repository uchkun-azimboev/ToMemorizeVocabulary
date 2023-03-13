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

    override suspend fun incrementWordCount(title: String) {
        categoryDao.incrementWordCount(title)
    }

    override suspend fun decrementWordCount(title: String) {
        categoryDao.decrementWordCount(title)
    }

    override suspend fun updateCategory(title: String, newTitle: String, description: String) {
        categoryDao.updateCategory(title, newTitle, description)
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }
}