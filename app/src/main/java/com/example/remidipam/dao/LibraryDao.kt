package com.example.remidipam.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.remidipam.data.local.entity.Book
import com.example.remidipam.data.local.entity.Category
import com.example.remidipam.data.local.entity.PhysicalBook
import com.example.remidipam.entity.Book
import com.example.remidipam.entity.Category

@Dao
interface LibraryDao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)


    @Query("SELECT * FROM categories WHERE isDeleted = 0")
    suspend fun getAllCategories(): List<Category>


    @Query("UPDATE categories SET isDeleted = 1 WHERE id = :categoryId")
    suspend fun softDeleteCategory(categoryId: Int)



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)


    @Query("UPDATE books SET isDeleted = 1 WHERE categoryId = :categoryId")
    suspend fun softDeleteBooksByCategory(categoryId: Int)


    @Query("UPDATE books SET categoryId = NULL WHERE categoryId = :categoryId")
    suspend fun uncategorizeBooks(categoryId: Int)



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhysicalBook(physicalBook: PhysicalBook)


    @Query("""
        SELECT COUNT(*) FROM physical_books pb
        JOIN books b ON pb.bookId = b.id
        WHERE b.categoryId = :categoryId 
        AND pb.status = 'BORROWED' 
        AND pb.isDeleted = 0
    """)
    suspend fun countBorrowedBooksInCategory(categoryId: Int): Int
}