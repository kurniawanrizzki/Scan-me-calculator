package com.noscale.ocr

import android.content.Intent
import android.net.Uri

class FileSystemsProvider : OCRProvider {
    override val intent: Intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        type = "image/*"
        addCategory(Intent.CATEGORY_OPENABLE)
        addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    override fun getResult(data: Intent?): Uri? {
        val clipData = data?.clipData

        clipData?.let {
            val item = it.getItemAt(0)
            return item.uri
        }

        return data?.data
    }
}