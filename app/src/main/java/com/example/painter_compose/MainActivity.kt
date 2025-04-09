package com.example.painter_compose


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Paint
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.drawText

import androidx.core.net.toUri

import com.example.painter_compose.ui.theme.Painter_ComposeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File

class MainActivity : ComponentActivity() {


    private lateinit var canvas1: Canvas
  //  var onStart = false
    var onStart = mutableStateOf(false)
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
    var strokeWidth = mutableStateOf(8f)
    var strokeCap = mutableStateOf(StrokeCap.Round)
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

              ModalNavigationDrawer( drawerState = drawerState, content = { Draw(); DrawMenu(); if(!onStart.value) { StartMenu() } }, drawerContent = { ModalMenu() } )

                 }
        }
    }


    @Composable
    fun Draw() {

        var path1 by remember {  mutableStateOf(Path()) }

         Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(key1 = Unit,key2 = Unit) {

                    detectTapGestures { t ->
                        if (t.y > height - heigthRect) {

                            menu.forEach {
                                if (t.x in it.key - radius..it.key + radius) {
                                    colorBrish.value = it.value;
                                }
                            }
                        } else {

                            if (onStart.value) {
                            points += PointData(
                                t.x,
                                t.y,
                                colorBrish.value,
                                strokeWidth.value
                            ); history += 'p'
                        }}

                        onStart.value = true

                  scope.launch {
                      detectDragGestures(

                          onDrag = { change: PointerInputChange, offset: Offset ->

                              tempPath.moveTo(
                                  change.position.x - offset.x,
                                  change.position.y - offset.y
                              )
                              tempPath.lineTo(change.position.x,change.position.y)


                              path1 = Path().apply {  addPath(tempPath) }


                          },

                          onDragStart = { tempPath = Path() },
                          onDragEnd = {
                              path.add(PathData(path1, colorBrish.value,strokeWidth.value,strokeCap.value)); path1 = Path(); history += 'l' })

                  }
                }



                }

         ) {

             if (width == 0f) { init(size.width,size.height) }

            drawRect(
                color = Color.Blue,
                size = Size(size.width,heigthRect),
                topLeft = Offset(0f, height - heigthRect),
                style = Stroke(width = 12f))

             menu.forEach { drawCircle(radius = radius, center = Offset((it.key) ,height - heigthRect/2), color = it.value) }

             path.forEach { drawPath(it.path,it.color, style = Stroke(it.width, cap = it.cap)) }

              points.forEach { drawCircle(radius = it.width/2, center = Offset(it.x,it.y), color = it.color) }
               drawPath(path1, color = colorBrish.value, style = Stroke(strokeWidth.value, cap = strokeCap.value))




        }


    }







    @Composable
    fun DrawMenu() {

        MainMenu(colorBrish.value,strokeWidth.value) {

                icon -> when(icon){
            100 -> { bitmap.value = drawCanvasToBitmap(width,height,path,points,getColor(R.color.white));  scope.launch { drawerState.open() } }
            111 -> { RemoveLast() }
            112 -> {  path.clear(); points.clear()  }
            113 -> { }
            114 -> finishAffinity()
            in 1..99 -> { strokeWidth.value = icon.toFloat()}

            201 -> { strokeCap.value = StrokeCap.Butt }
            202 -> {strokeCap.value = StrokeCap.Square }
            203 -> { strokeCap.value = StrokeCap.Round }
        }

        }
    }

    @Composable
    fun ModalMenu() {


        DrawerMenu(bitmap.value) { selected ->
            when (selected) {

                "Выход" -> { finishAffinity() }
                "Очистить экран" -> { path.clear(); points.clear(); history.clear(); scope.launch { drawerState.close() }; bitmap.value = null }
                "Сохранить" -> { SaveImage(bitmap.value,this); scope.launch {  drawerState.close() } }
                "Назад" -> { scope.launch { drawerState.close() } }
                "Поделиться" -> {  ShareBitmap(bitmap.value)  }

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
        if (history.size < 1) return
        Log.d("Mylog","${history.size}")
        when (history.get(history.size - 1)) {
            'l' -> path.removeAt(path.size - 1)
            'p' -> points.removeAt(points.size - 1)
        }
        history.removeAt(history.size -1)



    }
     fun ShareBitmap(bitmap: Bitmap?) {

        if (bitmap == null) return


        val patch = MediaStore.Images.Media.insertImage(contentResolver,bitmap, "temp",null)


       val uri = patch.toUri()
//
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("image/*")
       intent.putExtra(Intent.EXTRA_STREAM, uri)
       intent.putExtra(Intent.EXTRA_TEXT, " ")
         startActivityForResult(intent,111)


    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?

    ) {
        super.onActivityResult(requestCode, resultCode, data)
      //  Toast.makeText(this,"AAAAAAAAAAAAA",Toast.LENGTH_LONG).show()
        val filePatch = Environment.getExternalStorageDirectory().absolutePath + "/Pictures" + "/temp.jpg"
        val file = File(filePatch)
        file.delete()
    }
}


