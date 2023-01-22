package com.noscale.ocr.data.source

import com.noscale.ocr.data.Expression
import kotlinx.coroutines.flow.Flow

interface DataSource {
    fun read(): Flow<List<Expression>>
    suspend fun write(data: Expression)
}