package com.noscale.ocr.data.source.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.noscale.ocr.data.Expression
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpressionDao {
    @Query("SELECT * FROM ocr_expression ORDER BY id DESC")
    fun read(): Flow<List<Expression>>
    @Insert
    suspend fun insert(data: Expression)
}