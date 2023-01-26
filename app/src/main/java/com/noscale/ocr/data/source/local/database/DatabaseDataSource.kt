package com.noscale.ocr.data.source.local.database

import com.noscale.ocr.data.Expression
import com.noscale.ocr.data.source.DataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseDataSource @Inject constructor(private val dao: ExpressionDao) : DataSource {
    override fun read(): Flow<List<Expression>> =
        dao.read()

    override suspend fun write(data: Expression) =
        dao.insert(data)
}