package com.example.painter_compose

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap

data class PathData (val path: Path ,val color: Color,val width: Float,val cap: StrokeCap) {
}

data class PointData(val x: Float,val y: Float,val color: Color, val width: Float) {
}