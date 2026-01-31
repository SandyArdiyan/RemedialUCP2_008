package com.example.remidipam.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authors")
data class Author(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)

@Entity(
    tableName = "book_author_cross_ref",
    primaryKeys = ["bookId", "authorId"]
)
data class BookAuthorCrossRef(
    val bookId: Int,
    val authorId: Int
)