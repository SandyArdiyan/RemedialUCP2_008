package com.example.remidipam.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.remidipam.entity.Book

@Entity(
    tableName = "physical_books",
    foreignKeys = [
        ForeignKey(
            entity = Book::class,
            parentColumns = ["id"],
            childColumns = ["bookId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PhysicalBook(
    // ID Unik per fisik (Barcode/QR), bukan AutoGenerate
    @PrimaryKey val uniqueId: String,

    val bookId: Int,

    // Status: "AVAILABLE", "BORROWED", "LOST"
    val status: String,

    // Lokasi Rak (Opsional)
    val shelfLocation: String? = null,

    val isDeleted: Boolean = false
)