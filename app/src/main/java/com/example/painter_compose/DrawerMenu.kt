package com.example.painter_compose

import android.graphics.Bitmap
import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


@Composable
fun  DrawerMenu(bitmap: Bitmap?, onClick:(String) -> Unit) {
         val menu = Menu.menu
        val picture: ImageBitmap


        if (bitmap != null)  { picture = bitmap.asImageBitmap() } else { picture = ImageBitmap.imageResource(R.drawable.brish)}



    ModalDrawerSheet (drawerContainerColor = Color.Blue, modifier = Modifier.fillMaxWidth(0.9f)) {


       Row (modifier = Modifier.fillMaxWidth().padding(top = 32.dp), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
           Image( modifier = Modifier.width(120.dp).height(200.dp).background(Color.Black),
               bitmap = picture,
               contentDescription = "")
           Text(text = "PAINT", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
       }
        Column (modifier = Modifier.padding(top = 100.dp)){
            menu.forEach { NavigationDrawerItem(selected = false,

                label = { Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween)
                {


                    Text(text = it.name, fontSize = 24.sp, color = Color.White); Icon(it.icon, contentDescription = "", tint = Color.White, modifier = Modifier.size(32.dp))

                }

                           } ,
                onClick = {onClick(it.name)})}
            Divider(modifier = Modifier.fillMaxWidth().padding(top = 50.dp))
            NavigationDrawerItem( modifier = Modifier.padding(top = 50.dp),selected = false, label = { Row ( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = Menu.m10.name, fontSize = 22.sp, color = Color.White); Icon(Menu.m10.icon,
                contentDescription = "", tint = Color.White, modifier = Modifier.size(30.dp))
            }}, onClick = {onClick(Menu.m10.name)});

        }

      //  NavigationDrawerItem(selected = true, label = { Row { Text(text = "AAAAA"); Icon(Icons.Filled.Refresh, contentDescription = "") } }, onClick = {}, modifier = Modifier.padding())
      //  NavigationDrawerItem(selected = false, label = { Text("BBBBBBBBB") }, onClick = {}, modifier = Modifier.padding())

    }

}
class Menu(val name: String,val icon: ImageVector){
    companion object {
        val m1 = Menu("Очистить экран",Icons.Filled.Clear)
        val m2 = Menu("Сохранить",Icons.Filled.Done)
        val m3 = Menu("Поделиться",Icons.Filled.Share)
        val m10 = Menu("Выход",Icons.Filled.ExitToApp)
        val m4 = Menu("Назад",Icons.Filled.KeyboardArrowRight)
        val menu = listOf(m1,m2,m3,m4)

    }

}