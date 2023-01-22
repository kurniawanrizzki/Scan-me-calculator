package com.noscale.ocr.data.source.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.noscale.ocr.data.Expression
import com.noscale.ocr.util.Constant

@Database(entities = [Expression::class], version = 1, exportSchema = false)
abstract class OCRDatabase: RoomDatabase() {
    abstract fun expressionDao(): ExpressionDao

    companion object {
        @Volatile private var instance: OCRDatabase? = null

        fun getInstance(context: Context): OCRDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): OCRDatabase {
            return Room.databaseBuilder(context, OCRDatabase::class.java, Constant.DB_NAME)
                .build()
        }
    }
}