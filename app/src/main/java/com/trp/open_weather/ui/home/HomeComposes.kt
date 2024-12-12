package com.trp.open_weather.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trp.open_weather.data.tempDummy
import com.trp.open_weather.model.Temp
import com.trp.open_weather.ui.theme.MyApplicationTheme

@Composable
fun TempCompose(
    modifier: Modifier = Modifier,
    temp: Temp
){
    Column() {
        Card(
            modifier = modifier
                .width(150.dp)
                .height(100.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent.copy(alpha = 0.1f))
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = temp.temp.toString(),
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun TempComposePreview(){
    val temp = tempDummy
    MyApplicationTheme {
        TempCompose(temp = temp)
    }
}

