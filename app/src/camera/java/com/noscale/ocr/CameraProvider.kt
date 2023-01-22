package com.noscale.ocr

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException

class CameraProvider(private val context: Context) : OCRProvider {
    private var uri: Uri? = null

    override val intent: Intent by lazy {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            val file = try {
                createImageFileInAppDir(context)
            } catch (e: IOException) {
                null
            }

            file?.also { f ->
                uri = FileProvider.getUriForFile(
                    context,
                    "${BuildConfig.APPLICATION_ID}.provider",
                    f
                )
                putExtra(MediaStore.EXTRA_OUTPUT, uri)
            }
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }
    }

    override fun getResult(data: Intent?): Uri? =
        uri

    @kotlin.jvm.Throws(IOException::class)
    private fun createImageFileInAppDir(context: Context): File? {
        val path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File(path, "${System.currentTimeMillis()}.jpg")
    }
}