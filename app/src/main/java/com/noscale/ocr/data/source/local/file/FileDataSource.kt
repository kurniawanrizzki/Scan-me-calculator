package com.noscale.ocr.data.source.local.file

import android.content.Context
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import com.noscale.ocr.data.Expression
import com.noscale.ocr.data.source.DataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileDataSource @Inject constructor(@ApplicationContext private val context: Context) : DataSource {
    private val keyGenParameterSpec by lazy { MasterKeys.AES256_GCM_SPEC }
    private val mainKeyAlias by lazy { MasterKeys.getOrCreate(keyGenParameterSpec) }

    private val fileName by lazy {
        "secret_${context.packageName.replace(".", "_")}.text"
    }

    private val expressions: MutableStateFlow<List<Expression>> = MutableStateFlow(arrayListOf())

    private val currentExpressions: List<Expression>
        get() {
            try {
                val inputStream = getEncryptedFile(false).openFileInput()
                val byteArrayOutputStream = ByteArrayOutputStream()
                var nextByte = inputStream.read()

                while (nextByte != -1) {
                    byteArrayOutputStream.write(nextByte)
                    nextByte = inputStream.read()
                }

                val plainText = String(byteArrayOutputStream.toByteArray())
                return Json.decodeFromString(plainText)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return arrayListOf()
        }

    override fun read(): Flow<List<Expression>> =
        expressions.apply { value = currentExpressions }

    override suspend fun write(data: Expression) {
        val lastExpressions = currentExpressions.toMutableList().apply { add(0,data) }
        val content = Json.encodeToString(lastExpressions)
        val fileContent = content.toByteArray(StandardCharsets.UTF_8)

        expressions.value = lastExpressions

        getEncryptedFile(true).openFileOutput().apply {
            write(fileContent)
            flush()
            close()
        }
    }

    private fun getEncryptedFile(write: Boolean): EncryptedFile {
        val file = File(context.filesDir, fileName)

        if (write && file.exists())
            file.delete()

        return EncryptedFile.Builder(
            file,
            context,
            mainKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()
    }
}