package com.example.submissiondua.pref

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val TIMESTAMP_PATTERN = "yyyyMMdd_HHmmss"
private const val IMAGE_MAX_SIZE = 1_000_000

fun generateCurrentTimestamp(): String {
    return SimpleDateFormat(TIMESTAMP_PATTERN, Locale.US).format(Date())
}

fun createTemporaryFile(context: Context): File {
    val cacheDir = context.externalCacheDir ?: context.cacheDir
    return File.createTempFile(generateCurrentTimestamp(), ".jpg", cacheDir)
}

fun copyUriToFile(uri: Uri, context: Context): File {
    val tempFile = createTemporaryFile(context)
    context.contentResolver.openInputStream(uri)?.use { input ->
        FileOutputStream(tempFile).use { output ->
            input.copyTo(output)
        }
    }
    return tempFile
}

fun File.compressImageFile(): File {
    val bitmap = BitmapFactory.decodeFile(this.path).adjustImageOrientation(this)
    var quality = 100

    do {
        val byteArrayStream = ByteArrayOutputStream().apply {
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, this)
        }
        if (byteArrayStream.size() <= IMAGE_MAX_SIZE) break
        quality -= 5
    } while (quality > 0)

    return saveCompressedBitmapToFile(bitmap, quality)
}

private fun File.saveCompressedBitmapToFile(bitmap: Bitmap, quality: Int): File {
    FileOutputStream(this).use { fos ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos)
    }
    return this
}

fun Bitmap.adjustImageOrientation(file: File): Bitmap {
    val orientation = ExifInterface(file).getAttributeInt(
        ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED
    )
    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmapImage(this, 90F)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmapImage(this, 180F)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmapImage(this, 270F)
        else -> this
    }
}

private fun rotateBitmapImage(bitmap: Bitmap, angle: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(angle) }
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}
