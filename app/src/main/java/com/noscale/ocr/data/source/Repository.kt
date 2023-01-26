package com.noscale.ocr.data.source

import android.content.SharedPreferences
import com.noscale.ocr.data.Expression
import com.noscale.ocr.data.source.local.database.DatabaseDataSource
import com.noscale.ocr.data.source.local.file.FileDataSource
import com.noscale.ocr.util.Constant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val dbSource: DatabaseDataSource,
    private val fileSource: FileDataSource,
    private val sharedPreferences: SharedPreferences
) {
    var usingDb: Boolean
        set(value) {
            sharedPreferences.edit().apply {
                putBoolean(Constant.DB_USAGE_KEY, value)
            }.commit()
        }
        get() =
            sharedPreferences.getBoolean(Constant.DB_USAGE_KEY, true)

    fun read() = if (usingDb) dbSource.read()
    else fileSource.read()

    suspend fun write(data: Expression) = if (usingDb) dbSource.write(data)
    else fileSource.write(data)
}