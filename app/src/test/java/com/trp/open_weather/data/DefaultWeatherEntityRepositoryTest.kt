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

package com.trp.open_weather.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import com.trp.open_weather.data.local.database.WeatherEntity
import com.trp.open_weather.data.local.database.WeatherDao

/**
 * Unit tests for [DefaultWeatherRepository].
 */
@OptIn(ExperimentalCoroutinesApi::class) // TODO: Remove when stable
class DefaultWeatherEntityRepositoryTest {

//    @Test
//    fun weathers_newItemSaved_itemIsReturned() = runTest {
//        val repository = DefaultWeatherRepository(FakeWeatherDao())
//
//        repository.add("Repository")
//
//        assertEquals(repository.weathers.first().size, 1)
//    }

}

private class FakeWeatherDao : WeatherDao {

    private val data = mutableListOf<WeatherEntity>()

    override fun getWeathers(): Flow<List<WeatherEntity>> = flow {
        emit(data)
    }

    override suspend fun insertWeather(item: WeatherEntity) {
        data.add(0, item)
    }
}
