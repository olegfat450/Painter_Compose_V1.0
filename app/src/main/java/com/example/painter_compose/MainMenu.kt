package com.example.painter_compose

import android.content.Context
import android.graphics.drawable.Icon
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

 val capMenu = listOf(201,202,203)

@Composable
fun MainMenu(color: Color,strikeWeight: Float, onClick: (Int) -> Unit) {



    var fontMenu by remember { mutableStateOf(false)}
    var fontWeight by remember { mutableStateOf(strikeWeight) }
    var cap by remember { mutableStateOf(capMenu.get(2)) }
    var capMenuOn by remember { mutableStateOf(false) }

    Row (modifier = Modifier.fillMaxWidth().height(52.dp).background(Color.LightGray), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {


        Icon(Icons.Filled.Menu, contentDescription = "", modifier = Modifier.size(42.dp).clickable { onClick(100) })
        Icon(Icons.Filled.Edit, contentDescription = "", modifier = Modifier.size(42.dp), tint = color)

        Row (modifier = Modifier.widthIn(60.dp), horizontalArrangement = Arrangement.Center) {


            Text(
                text = "${fontWeight.toInt()}f",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable(onClick = { fontMenu = true })
            )



            DropdownMenu(
                expanded = fontMenu,
                onDismissRequest = { fontMenu = false },
                modifier = Modifier.size(height = 360.dp, width = 60.dp,)
            ) {


                Slider(
                    fontWeight, onValueChange = { fontWeight = it; onClick(it.toInt()) },
                    valueRange = 1f..99f, modifier = Modifier.rotate(90f)
                        .layout { measurable, constraints ->

                            val placeable = measurable.measure(
                                Constraints(
                                    minWidth = constraints.minHeight,
                                    maxWidth = constraints.maxHeight,
                                    minHeight = constraints.minWidth,
                                    maxHeight = constraints.maxWidth
                                )
                            )
                            layout(
                                placeable.width,
                                placeable.height
                            ) { placeable.place(placeable.width / 2, 0) }

                        }.width(300.dp).height(60.dp)
                )
            }
        }

          Row(modifier = Modifier.widthIn(60.dp).clickable(onClick = {capMenuOn = true}), horizontalArrangement = Arrangement.Center)
          { Text (text = "x${cap.toString().get(2)}", fontSize = 22.sp, fontWeight = FontWeight.Bold)

           DropdownMenu(expanded = capMenuOn, onDismissRequest = { capMenuOn = false}, modifier = Modifier.width(60.dp).height(180.dp)) {

               capMenu.forEach { DropdownMenuItem(text = {  Text(modifier = Modifier.fillMaxSize(), text = "x${it.toString().get(2)}", fontSize = 24.sp, textAlign = TextAlign.Center)},
                   onClick = {onClick(it); capMenuOn = false; cap = it}) }

           }


          }





        Icon(Icons.Filled.Refresh, contentDescription = "", modifier = Modifier.size(42.dp).clickable { onClick(111)}, tint = Color.Blue)
//        Icon(Icons.Filled.Clear, contentDescription = "", modifier = Modifier.size(42.dp).clickable { onClick(2) }, tint = Color.Red)
//        Icon(Icons.Filled.Share, contentDescription = "", modifier = Modifier.size(42.dp).clickable { onClick(3) })
//        Icon(Icons.Filled.ExitToApp, contentDescription = "", modifier = Modifier.size(42.dp).clickable { onClick(4) })




    }


}





















