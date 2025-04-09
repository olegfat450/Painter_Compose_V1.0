package com.example.painter_compose

import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.compose.runtime.Composable



fun SaveImage(bitmap: Bitmap?,context: Context) {

    if (bitmap == null) return

    val fileName = "Myopic"

    MediaStore.Images.Media.insertImage(context.contentResolver,bitmap, fileName,null)
}