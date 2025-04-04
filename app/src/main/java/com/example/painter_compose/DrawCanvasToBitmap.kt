package com.example.painter_compose

import android.graphics.Bitmap
import android.graphics.Paint.Style
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.createBitmap
import com.example.painter_compose.ui.theme.PointData

fun drawCanvasToBitmap(width: Float, height: Float, path: SnapshotStateList<PathData>, points: MutableList<PointData>): Bitmap? {

      if (path.isEmpty() && points.isEmpty()) { return null }

   val bitmap = createBitmap(width.toInt(),height.toInt())
    val canvas = android.graphics.Canvas(bitmap)

    val p = android.graphics.Paint()


    p.style = Style.STROKE
    p.strokeWidth = 12f


  points.forEach { p.color = it.color.toArgb(); canvas.drawCircle(it.x,it.y,12f, p) }

   path.forEach { p.color = it.color.toArgb(); canvas.drawPath(it.path.asAndroidPath(),p) }




    return bitmap

}