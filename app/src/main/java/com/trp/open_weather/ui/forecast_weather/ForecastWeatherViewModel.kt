package com.trp.open_weather.ui.forecast_weather

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.trp.open_weather.data.Result
import com.trp.open_weather.data.forecast.ForecastRepository
import com.trp.open_weather.data.weather.WeatherRepository
import com.trp.open_weather.model.Temp
import com.trp.open_weather.ui.ForecastWeather
import com.trp.open_weather.utils.makeWord
import com.trp.open_weather.utils.utcToDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException
import javax.inject.Inject

data class ForecastWeatherUiState(
    val isLoading: Boolean = false,
    val userMessage: String? = null,
    val items: List<Temp>? = null
)

@HiltViewModel
class ForecastWeatherViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val forecastRepository: ForecastRepository,
) : ViewModel() {

    private val forecastWeather = savedStateHandle.toRoute<ForecastWeather>()
    private val date = utcToDate(forecastWeather.dateTime)
    val dateString = "${date.dayOfMonth} ${makeWord(date.month.name)}"

    val uiState = MutableStateFlow(ForecastWeatherUiState(isLoading = true))

    fun fetchForecastWeather(latitude: Double, longitude: Double){
        uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try{
                val result = forecastRepository.getForecastWeatherLocation(latitude = latitude, longitude = longitude)
                uiState.update {
                    when(result){
                        is Result.Success -> {
                            it.copy(isLoading = false, items = result.data.filter { item ->
                                date.toLocalDate().isEqual(utcToDate(item.dateTime).toLocalDate())
                            })
                        }
                        is Result.Error -> {
                            it.copy(userMessage = result.exception.message, isLoading = true)
                        }
                    }
                }
            } catch (e: IOException){
                uiState.update {
                    ForecastWeatherUiState(
                        isLoading = false,
                        userMessage = e.message
                    )
                }
            }
        }
    }

    fun userMessageShow(){
        uiState.update { ForecastWeatherUiState(userMessage = null) }
    }
}