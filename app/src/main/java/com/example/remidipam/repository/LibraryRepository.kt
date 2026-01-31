package com.example.remidipam.repository

import androidx.room.withTransaction
import com.example.remidipam.dao.LibraryDao
import com.example.remidipam.data.local.dao.LibraryDao
import com.example.remidipam.entity.AppDatabase
import com.example.remidipam.entity.Category
import javax.inject.Inject

class LibraryRepository @Inject constructor(
    private val db: AppDatabase,
    private val dao: LibraryDao
) {


    suspend fun deleteCategorySecurely(categoryId: Int, deleteBooks: Boolean) {

        db.withTransaction {

            val borrowedCount = dao.countBorrowedBooksInCategory(categoryId)

            if (borrowedCount > 0) {
                throw IllegalStateException("GAGAL: Ada $borrowedCount buku sedang dipinjam di kategori ini. Operasi dibatalkan.")
            }


            if (deleteBooks) {
                dao.softDeleteBooksByCategory(categoryId)
            } else {
                dao.uncategorizeBooks(categoryId)
            }

            dao.softDeleteCategory(categoryId)
        }
    }


    suspend fun updateCategoryParent(targetCategoryId: Int, newParentId: Int?) {
        if (newParentId == null) {

            return
        }

        val allCategories = dao.getAllCategories()

        if (isCyclic(targetCategoryId, newParentId, allCategories)) {
            throw IllegalArgumentException("Error: Perpindahan ini menyebabkan Cyclic Reference (Loop tak terbatas).")
        }


    }


    private fun isCyclic(targetId: Int, newParentId: Int, allCats: List<Category>): Boolean {
        var currentParentId: Int? = newParentId

        while (currentParentId != null) {
            if (currentParentId == targetId) return true

            val parentCat = allCats.find { it.id == currentParentId }
            currentParentId = parentCat?.parentId
        }
        return false
    }
}