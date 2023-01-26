package com.noscale.ocr.di

import android.content.Context
import android.content.SharedPreferences
import com.noscale.ocr.data.source.local.database.ExpressionDao
import com.noscale.ocr.data.source.local.database.OCRDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun provideOcrDatabase(@ApplicationContext context: Context): OCRDatabase =
        OCRDatabase.getInstance(context)

    @Provides
    fun provideExpressionDao(db: OCRDatabase): ExpressionDao =
        db.expressionDao()

    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
}