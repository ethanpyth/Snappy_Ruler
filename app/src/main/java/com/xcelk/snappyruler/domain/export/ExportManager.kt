package com.xcelk.snappyruler.domain.export

import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream

object ExportManager {
    fun saveBitmap(bitmap: Bitmap, file: File) {
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
    }
}