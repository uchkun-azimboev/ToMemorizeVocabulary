package uz.pdp.tomemorizevocabulary.repository

import androidx.room.Query
import uz.pdp.tomemorizevocabulary.data.local.entity.Category

interface CategoryRepository {
    suspend fun insert(category: Category)
    suspend fun getAllCategory(): List<Category>
    suspend fun incrementWordCount(title: String)
    suspend fun decrementWordCount(title: String)
    suspend fun updateCategory(title: String, newTitle: String, description: String)
    suspend fun deleteCategory(category: Category)
}