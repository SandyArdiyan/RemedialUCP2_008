package com.example.remidipam.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audit_logs")
data class AuditLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tableName: String,
    val action: String,
    val previousData: String?,
    val newData: String?,
    val timestamp: Long = System.currentTimeMillis()
)