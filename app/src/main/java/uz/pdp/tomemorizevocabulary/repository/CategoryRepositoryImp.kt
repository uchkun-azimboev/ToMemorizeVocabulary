package uz.pdp.tomemorizevocabulary.repository

import uz.pdp.tomemorizevocabulary.data.local.dao.CategoryDao
import uz.pdp.tomemorizevocabulary.model.Category

class CategoryRepositoryImp(private val categoryDao: CategoryDao) : CategoryRepository {
    override suspend fun insert(category: Category) {
        categoryDao.insert(category)
    }

    override suspend fun getAllCategory(): List<Category> {
        return categoryDao.getAllCategory()
    }
}