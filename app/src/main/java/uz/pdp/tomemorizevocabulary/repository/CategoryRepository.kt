package uz.pdp.tomemorizevocabulary.repository

import uz.pdp.tomemorizevocabulary.model.Category

interface CategoryRepository {
    suspend fun insert(category: Category)
    suspend fun getAllCategory(): List<Category>
}