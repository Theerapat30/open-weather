package com.trp.open_weather.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trp.open_weather.R
import com.trp.open_weather.data.tempDummy
import com.trp.open_weather.model.Temp
import com.trp.open_weather.ui.theme.MyApplicationTheme
import com.trp.open_weather.ui.theme.PrimaryFontColor
import com.trp.open_weather.ui.theme.SunnyColor
import com.trp.open_weather.ui.theme.Typography

@Composable
fun TempCompose(
    modifier: Modifier = Modifier,
    dateString: String,
    temp: Temp
){
    Column(
        modifier = modifier.padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .width(180.dp)
                .height(100.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent.copy(alpha = 0.025f))
        ) {
            Box(modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp)) {
                Text(
                    text = dateString,
                    modifier = Modifier.align(Alignment.TopCenter),
                    style = Typography.titleSmall,
                    color = PrimaryFontColor
                )
                Text(
                    text = temp.tempDisplay,
                    modifier = Modifier.align(Alignment.Center),
                    style = Typography.headlineLarge,
                    color = PrimaryFontColor
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TempSuffixCompose(imgId = R.drawable.ic_thermometer_minus_24, info = temp.minTempDisplay, suffix = "min")
            TempSuffixCompose(imgId = null, info = temp.tempFeelsDisplay, suffix = "feels")
            TempSuffixCompose(imgId = R.drawable.ic_thermometer_add_24, info = temp.minTempDisplay, suffix = "max")
        }
    }
}

@Composable
fun TempSuffixCompose(@DrawableRes imgId: Int?, info: String, suffix: String){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (imgId != null){
            Icon(
                painter = painterResource(imgId),
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.size(4.dp))
        }
        Text(text = info, color = PrimaryFontColor, style = Typography.bodyMedium)
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = suffix, color = PrimaryFontColor, style = Typography.bodyMedium)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFBDE3F0)
@Composable
fun TempComposePreview(){
    val temp = tempDummy
    MyApplicationTheme {
        TempCompose(
            dateString = "Monday, 09 December",
            temp = temp
        )
    }
}

