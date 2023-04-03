package uz.pdp.tomemorizevocabulary.repository

import androidx.room.Query
import uz.pdp.tomemorizevocabulary.data.local.entity.Category

interface CategoryRepository {
    suspend fun insert(category: Category)
    suspend fun getAllCategory(): List<Category>
    suspend fun incrementWordCount(id: Int)
    suspend fun decrementWordCount(id: Int)
    suspend fun updateCategory(id: Int, newTitle: String, description: String)
    suspend fun deleteCategory(category: Category)
}