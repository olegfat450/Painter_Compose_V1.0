package com.example.painter_compose


import android.graphics.Bitmap
import android.graphics.Paint.Style
import android.graphics.PathEffect
import android.graphics.Point
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.GraphicsContext
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.painter_compose.ui.theme.Painter_ComposeTheme
import com.example.painter_compose.ui.theme.PointData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.graphics.createBitmap

class MainActivity : ComponentActivity() {


    private lateinit var canvas1: Canvas
    var flag = false
    var width = 0f
    var height = 0f
    var stepMenu = 0f
    var menu = mutableMapOf<Float,Color>()
    var sizeMenu = Size(0f,0f)
    var heigthRect = 140f
    val radius = heigthRect/2 -18f
    val menuColors = listOf(Color.Black,Color.Red,Color.Blue,Color.Green,Color.Magenta,Color.Yellow)
    var colorBrish = mutableStateOf(menuColors.get(0))
    var path = mutableStateListOf<PathData>()
    var tempPath = Path()
    val points: MutableList<PointData> = mutableStateListOf()
    var strokeWidth = 12f
    val history = mutableListOf<Char>()
       private lateinit var scope: CoroutineScope
       var bitmap: MutableState<Bitmap?> = mutableStateOf(null)
       private lateinit var drawerState: DrawerState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        

        setContent {

            scope = rememberCoroutineScope()

           drawerState = rememberDrawerState(DrawerValue.Closed)
            Painter_ComposeTheme {

              ModalNavigationDrawer( drawerState = drawerState, content = { Draw(); DrawMenu() }, drawerContent = { ModalMenu() } )

                 }
        }
    }


    @Composable
    fun Draw() {

        var path1 by remember {  mutableStateOf(Path()) }

         Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(key1 = null) {
                    detectTapGestures { t ->    if ( t.y > height - heigthRect) {

                        menu.forEach { if (t.x in it.key - radius..it.key + radius) { colorBrish.value = it.value;} } } else
                   // { if (flag)
                        points += PointData(t.x,t.y,colorBrish.value); history += 'p'
                  //  }
                     //   flag = true

                  scope.launch {   detectDragGestures(
                                onDragStart = { tempPath = Path() },

                                onDragEnd = {
                                    path.add(PathData(tempPath, colorBrish.value)); path1 = Path(); history += 'l' })

                            { change, dragAmount ->

                                if ((change.position.y < size.height - heigthRect - 48f) && ((change.position.y - dragAmount.y - 48f) < (size.height - heigthRect))) {
                                    tempPath.moveTo(dragAmount.x,dragAmount.y)

                                    tempPath.moveTo(
                                        change.position.x - dragAmount.x,
                                        change.position.y - dragAmount.y
                                    )
                                    tempPath.lineTo(change.position.x, change.position.y)

                                path1 = Path().apply {  addPath(tempPath) }

                                }
                            }}}



                }) {

             if (width == 0f) { init(size.width,size.height) }

            drawRect(
                color = Color.Blue,
                size = Size(size.width,heigthRect),
                topLeft = Offset(0f, height - heigthRect),
                style = Stroke(width = 12f))

             menu.forEach { drawCircle(radius = radius, center = Offset((it.key) ,height - heigthRect/2), color = it.value) }

             path.forEach { drawPath(it.path,it.color, style = Stroke(strokeWidth)) }

              points.forEach { drawCircle(radius = strokeWidth, center = Offset(it.x,it.y), color = it.color) }
               drawPath(path1, color = colorBrish.value, style = Stroke(strokeWidth))

        }


    }


    @Composable
    fun DrawMenu() {

        MainMenu(colorBrish.value) {

                icon -> when(icon){
            0 -> { bitmap.value = drawCanvasToBitmap(width,height,path,points);  scope.launch { drawerState.open() } }
            1 -> { RemoveLast() }
            2 -> {  path.clear(); points.clear()  }
            3 -> { }
            4 -> finishAffinity()
        }

        }
    }

    @Composable
    fun ModalMenu() {


        DrawerMenu(bitmap.value) { selected ->
            when (selected) {
                "Выход" -> { finishAffinity() }
                "Очистить экран" -> { path.clear(); points.clear(); history.clear(); scope.launch { drawerState.close() }; bitmap.value = null }
                "Сохранить" -> { scope.launch {  drawerState.close() } }
                "Назад" -> { scope.launch { drawerState.close() } }
            }
        }

    }


    private fun init(w: Float,h: Float) {
        width = w
        height = h
        stepMenu = width/(menuColors.size*2)
        sizeMenu = Size(width = width, height = heigthRect)
        menuColors.forEachIndexed { index, color -> menu.put ((index * stepMenu*2 + stepMenu) , color)}

    }

     fun RemoveLast() {
        if (history.size <= 1) return
        Log.d("Mylog","${history.size}")
        when (history.get(history.size - 1)) {
            'l' -> path.removeAt(path.size - 1)
            'p' -> points.removeAt(points.size - 1)
        }
        history.removeAt(history.size -1)



    }


}


