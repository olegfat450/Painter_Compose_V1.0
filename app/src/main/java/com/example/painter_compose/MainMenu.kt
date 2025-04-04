package com.example.painter_compose

import android.content.Context
import android.graphics.drawable.Icon
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp


@Composable
fun MainMenu(color: Color, onClick: (Int) -> Unit) {



    Row (modifier = Modifier.fillMaxWidth().height(52.dp).background(Color.LightGray), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {


        Icon(Icons.Filled.Menu, contentDescription = "", modifier = Modifier.size(42.dp).clickable { onClick(0) })
        Icon(Icons.Filled.Edit, contentDescription = "", modifier = Modifier.size(42.dp), tint = color)
        Icon(Icons.Filled.Refresh, contentDescription = "", modifier = Modifier.size(42.dp).clickable { onClick(1)}, tint = Color.Blue)
//        Icon(Icons.Filled.Clear, contentDescription = "", modifier = Modifier.size(42.dp).clickable { onClick(2) }, tint = Color.Red)
//        Icon(Icons.Filled.Share, contentDescription = "", modifier = Modifier.size(42.dp).clickable { onClick(3) })
//        Icon(Icons.Filled.ExitToApp, contentDescription = "", modifier = Modifier.size(42.dp).clickable { onClick(4) })



    }


}