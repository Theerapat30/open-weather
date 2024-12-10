package com.trp.open_weather.ui.composes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trp.open_weather.R
import com.trp.open_weather.ui.theme.MyApplicationTheme
import com.trp.open_weather.ui.theme.Typography

@Composable
fun WeatherInfoItem(
    modifier: Modifier = Modifier,
    iconId: Int,
    label: String,
    info: String
){
    val spaceSize = 8.dp
    Row (
        modifier = modifier
            .width(250.dp)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(iconId), contentDescription = null)
            Spacer(modifier = Modifier.size(spaceSize))
            Text(
                label,
                modifier = Modifier
                    .padding(4.dp),
                style = Typography.titleLarge
            )
        }
        
        Text(info, style = Typography.bodyLarge)
    }
}



@Preview(showBackground = true)
@Composable
fun WeatherInfoItemPreview(){
    MyApplicationTheme {
        WeatherInfoItem(
            modifier = Modifier,
            iconId = R.drawable.ic_wind_24,
            label = "Weather",
            info = "30"
        )
    }
}