package com.noscale.ocr

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.mlkit.vision.text.Text

interface OCRProvider {
    val intent: Intent

    fun getResult(data: Intent?): Uri?
}