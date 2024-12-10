package com.trp.open_weather.ui.forecast

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trp.open_weather.data.ForecastRepository
import com.trp.open_weather.data.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val forecastRepository: ForecastRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow<ForecastUiState>(ForecastUiState.Success(Weather()))
    val uiState: StateFlow<ForecastUiState> = _uiState

    fun searchLocation(locationName: String){
        _uiState.update {
            ForecastUiState.Loading
        }
        viewModelScope.launch {
            forecastRepository.weathers(locationName)
                .catch { exception ->
                    Log.e("TAG", "searchLocation: ", exception)
                    _uiState.value = ForecastUiState.Error(exception)
                }
                .collect{ weather ->
                    _uiState.value = ForecastUiState.Success(weather)
                }
        }
    }

    fun reset(){
        _uiState.value = ForecastUiState.Success(Weather())
    }
}

sealed class ForecastUiState {
    object Loading: ForecastUiState()
    data class Success(val weather: Weather): ForecastUiState()
    data class Error(val exception: Throwable): ForecastUiState()
}