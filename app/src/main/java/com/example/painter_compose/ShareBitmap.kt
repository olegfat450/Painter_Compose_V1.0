package com.example.painter_compose

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult


import androidx.core.graphics.createBitmap
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream

 suspend fun ShareBitmap(bitmap: Bitmap?,context: Context) {

    if (bitmap == null) return

    val fileName = "temp"

   val patch = MediaStore.Images.Media.insertImage(context.contentResolver,bitmap, fileName,null)


  val uri = patch.toUri()

        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("image/*")
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.putExtra(Intent.EXTRA_TEXT, " ")

        context.startActivity(intent)




   val filePatch = Environment.getExternalStorageDirectory().absolutePath + "/Pictures" + "/${fileName}.jpg"

    val file = File(filePatch)


    file.delete()
 }