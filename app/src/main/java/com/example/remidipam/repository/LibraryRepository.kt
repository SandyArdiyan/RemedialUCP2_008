package com.example.remidipam.repository

import androidx.room.withTransaction
import com.example.remidipam.dao.LibraryDao       // [IMPORT YANG BENAR]
import com.example.remidipam.entity.AppDatabase   // [IMPORT YANG BENAR]
import com.example.remidipam.entity.Category
import javax.inject.Inject

class LibraryRepository @Inject constructor(
    private val db: AppDatabase, // Ini yang tadi error "NonExistentClass" karena salah import
    private val dao: LibraryDao
) {
    suspend fun deleteCategorySecurely(categoryId: Int, deleteBooks: Boolean) {
        db.withTransaction {
            val borrowedCount = dao.countBorrowedBooksInCategory(categoryId)
            if (borrowedCount > 0) {
                throw IllegalStateException("GAGAL: Ada $borrowedCount buku sedang dipinjam di kategori ini.")
            }

            if (deleteBooks) {
                dao.softDeleteBooksByCategory(categoryId)
            } else {
                dao.uncategorizeBooks(categoryId)
            }
            dao.softDeleteCategory(categoryId)
        }
    }

    // ... kode isCyclic lainnya tetap sama ...
}