package com.example.painter_compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun StartMenu() {

    Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {

        Row(modifier = Modifier.fillMaxWidth().height(300.dp).padding(start = 12.dp, end = 12.dp)
            .background(color = Color.LightGray, shape = RoundedCornerShape(12.dp))
            .border(width = 4.dp, color = Color.Black, shape = RoundedCornerShape(12.dp)), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {

            Text(text = "Выберите цвет", fontSize = 32.sp, fontWeight = FontWeight.Bold)

        }



}}