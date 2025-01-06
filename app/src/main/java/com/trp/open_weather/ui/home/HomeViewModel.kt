package com.trp.open_weather.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trp.open_weather.data.Result
import com.trp.open_weather.data.weather.WeatherRepository
import com.trp.open_weather.model.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface HomeUiState{
    val isLoading: Boolean
    val errorMessage: String

    data class NoWeather(
        override val isLoading: Boolean,
        override val errorMessage: String
    ): HomeUiState

    data class HasWeather(
        val weather: Weather,
        override val isLoading: Boolean,
        override val errorMessage: String
    ): HomeUiState

}

private data class HomeViewModelState(
    val weather: Weather? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
){
    fun toUiState(): HomeUiState =
        if (weather == null){
            HomeUiState.NoWeather(
                isLoading = isLoading,
                errorMessage = errorMessage
            )
        } else {
          HomeUiState.HasWeather(
              weather = weather,
              isLoading = isLoading,
              errorMessage = errorMessage
          )
        }
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel(){

    private val viewModelState = MutableStateFlow(
        HomeViewModelState(
            isLoading = true,
        )
    )

    val uiState: StateFlow<HomeUiState> = viewModelState
        .map(HomeViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

//    init {
//        Log.d("TAG", "HomeViewModel init...")
//        viewModelScope.launch {
//            fetchWeather("Bangkok")
//
//            weatherRepository.observeWeather().collect{ weather ->
//                viewModelState.update { it.copy(weather = weather) }
//            }
//        }
//    }

    // Fetch weather from location name
    fun fetchWeather(locationName: String){
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = weatherRepository.getWeather(locationName)
            viewModelState.update {
                when(result){
                    is Result.Success -> it.copy(weather = result.data, isLoading = false)
                    is Result.Error -> {
                        val errorMessage = it.errorMessage + "Something went wrong"
                        it.copy(errorMessage = errorMessage, isLoading = false)
                    }
                }
            }
        }
    }

    // Fetch weather from position location
    fun fetchWeather(latitude: Double, longitude: Double){
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = weatherRepository.getWeatherLocation(latitude = latitude, longitude = longitude)
            viewModelState.update {
                when(result){
                    is Result.Success -> {
                        it.copy(weather = result.data, isLoading = false)
                    }
                    is Result.Error -> {
                        val errorMessage = it.errorMessage + "Something went wrong"
                        it.copy(errorMessage = errorMessage, isLoading = false)
                    }
                }
            }
        }
    }

}