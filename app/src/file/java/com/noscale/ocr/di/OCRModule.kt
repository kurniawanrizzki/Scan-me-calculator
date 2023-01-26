package com.noscale.ocr.di

import com.noscale.ocr.FileSystemsProvider
import com.noscale.ocr.OCRProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class OCRModule {
    @Provides
    fun provideOCRProvider(): OCRProvider =
        FileSystemsProvider()
}