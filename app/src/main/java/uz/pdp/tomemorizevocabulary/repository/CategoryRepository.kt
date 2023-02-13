package uz.pdp.tomemorizevocabulary.repository

import uz.pdp.tomemorizevocabulary.data.local.entity.Category

interface CategoryRepository {
    suspend fun insert(category: Category)
    suspend fun getAllCategory(): List<Category>
}