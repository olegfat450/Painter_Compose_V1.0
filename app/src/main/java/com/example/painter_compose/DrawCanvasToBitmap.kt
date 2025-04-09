package com.example.painter_compose

import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Paint.Style
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.createBitmap


fun drawCanvasToBitmap(
    width: Float,
    height: Float,
    path: SnapshotStateList<PathData>,
    points: MutableList<PointData>,
    color: Int
): Bitmap? {

      if (path.isEmpty() && points.isEmpty()) { return null }

   val bitmap = createBitmap(width.toInt(),height.toInt())
    val canvas = android.graphics.Canvas(bitmap)



    val p = android.graphics.Paint()


   // p.style = Style.STROKE
    p.style = Style.FILL_AND_STROKE

    val c: Int = R.color.white

    canvas.drawColor(color)

  points.forEach { p.color = it.color.toArgb(); canvas.drawCircle(it.x,it.y,it.width/2, p) }

   path.forEach { p.color = it.color.toArgb(); p.strokeWidth = it.width; p.strokeCap = stokeCapToPaintCap(it.cap); canvas.drawPath(it.path.asAndroidPath(),p) }
    return bitmap



}
fun stokeCapToPaintCap(strokeCap: StrokeCap): Paint.Cap? {

    when (strokeCap) {
        StrokeCap.Square -> {return Paint.Cap.SQUARE}
        StrokeCap.Round -> { return Paint.Cap.ROUND }
        StrokeCap.Butt -> { return Paint.Cap.BUTT}
    }
    return null
}
