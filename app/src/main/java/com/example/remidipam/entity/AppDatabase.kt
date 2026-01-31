package com.example.remidipam.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.remidipam.dao.LibraryDao
import com.example.remidipam.dao.AuditDao
import com.example.remidipam.data.local.dao.AuditDao
import com.example.remidipam.data.local.dao.LibraryDao
import com.example.remidipam.data.local.entity.PhysicalBook

@Database(
    entities = [Category::class, PhysicalBook::class, Book::class, AuditLog::class, Author::class, BookAuthorCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun libraryDao(): LibraryDao
    abstract fun auditDao(): AuditDao
}