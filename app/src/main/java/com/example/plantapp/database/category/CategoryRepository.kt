package com.example.plantapp.database.category

class CategoryRepository(private val dao: CategoryDao) {
    suspend fun addCategory(category: Category) = dao.insert(category)
    suspend fun getAllCategories() = dao.getAllCategories()
    suspend fun getCategory(id: Int) = dao.get(id)
    suspend fun updateCategory(category: Category) = dao.update(category)
    suspend fun deleteCategory(category: Category) = dao.delete(category)
}