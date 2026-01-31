package com.example.remidipam.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.remidipam.entity.AuditLog

@Dao
interface AuditDao {


    @Insert
    suspend fun insertLog(log: AuditLog)


    @Query("SELECT * FROM audit_logs ORDER BY timestamp DESC")
    suspend fun getAllLogs(): List<AuditLog>
}