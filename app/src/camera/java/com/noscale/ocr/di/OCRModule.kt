package com.noscale.ocr.di

import android.content.Context
import com.noscale.ocr.CameraProvider
import com.noscale.ocr.OCRProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class OCRModule {
    @Provides
    fun provideOCRProvider(@ApplicationContext context: Context): OCRProvider =
        CameraProvider(context)
}