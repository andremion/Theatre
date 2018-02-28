/*
 * Copyright (c) 2018. André Mion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.andremion.data.local

import com.andremion.data.local.dao.EventTypeDao
import com.andremion.data.local.model.EventTypeLocalModel
import io.reactivex.Maybe
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@Suppress("IllegalIdentifier")
@RunWith(MockitoJUnitRunner::class)
/**
 * Enable the option to mock final classes is still a bit experimental, and requires a manual activation.
 * @see https://antonioleiva.com/mockito-2-kotlin/
 */
class EventTypeLocalDataSourceTest {

    @Mock
    private lateinit var eventTypeDao: EventTypeDao

    private lateinit var eventTypeLocalDataSource: EventTypeLocalDataSource

    @Before
    fun setUp() {
        eventTypeLocalDataSource = EventTypeLocalDataSource(eventTypeDao)
    }

    @Test
    @Throws(Exception::class)
    fun `Given local items data, When get event Types, Should fetch data from database`() {

        // Given

        val items = listOf(EventTypeLocalModel(1, "name"))
        `when`(eventTypeDao.getAll()).thenReturn(Maybe.just(items))

        // When

        val testObserver = eventTypeLocalDataSource.getAll()
                .test()

        // Should

        testObserver
                .assertComplete()
                .assertNoErrors()
                .assertValue(items)
    }
}