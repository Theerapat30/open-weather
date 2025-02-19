/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trp.open_weather.data.di

import com.trp.open_weather.BuildConfig
import com.trp.open_weather.data.CurrentWeatherOpenWeatherRepository
import com.trp.open_weather.data.CurrentWeatherRepository
import com.trp.open_weather.data.ForecastWeatherOpenWeatherRepository
import com.trp.open_weather.data.ForecastWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

/*
    Naming conventions [type of data + Repository]
*/

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindWeatherRepository(
        weatherRepository: CurrentWeatherOpenWeatherRepository
    ): CurrentWeatherRepository

    @Singleton
    @Binds
    fun bindForecastRepository(
        forecastRepository: ForecastWeatherOpenWeatherRepository
    ): ForecastWeatherRepository

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OpenWeatherApiKey

@Module
@InstallIn(SingletonComponent::class)
class KeyModule {

    @OpenWeatherApiKey
    @Provides
    fun provideOpenWeatherApiKey(): String {
        return BuildConfig.API_KEY
    }
}