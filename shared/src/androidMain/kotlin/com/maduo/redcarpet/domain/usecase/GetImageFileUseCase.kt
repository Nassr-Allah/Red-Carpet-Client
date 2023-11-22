package com.maduo.redcarpet.domain.usecase

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns

actual typealias GetImageFileUseCase = ImageUri

actual fun GetImageFileUseCase.toByteArray(): ByteArray = contentResolver.openInputStream(uri)?.use {
    it.readBytes()
} ?: throw IllegalStateException("Could not open stream $uri")


actual fun GetImageFileUseCase.getFileName(): String = contentResolver.query(
    uri, null, null, null, null
)?.use { cursor ->
    cursor.moveToFirst()
    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    cursor.getString(nameIndex)
} ?: throw Exception("Error in $uri")

class ImageUri(val uri: Uri, val contentResolver: ContentResolver)
