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

package com.andremion.data.repository

import com.andremion.data.local.EventLocalDataSource
import com.andremion.data.local.model.EventLocalModel
import com.andremion.data.remote.EventRemoteDataSource
import com.andremion.data.remote.model.EventRemoteModel
import com.andremion.data.repository.mapper.EventMapper
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@Suppress("IllegalIdentifier")
@RunWith(MockitoJUnitRunner::class)
/**
 * Enable the option to mock final classes is still a bit experimental, and requires a manual activation.
 * @see https://antonioleiva.com/mockito-2-kotlin/
 */
class EventRepositoryTest {

    @Mock
    private lateinit var eventLocalDataSource: EventLocalDataSource
    @Mock
    private lateinit var eventRemoteDataSource: EventRemoteDataSource
    @Mock
    private lateinit var eventMapper: EventMapper

    private lateinit var eventRepository: EventRepository

    @Before
    fun setUp() {
        eventRepository = EventRepository(eventLocalDataSource, eventRemoteDataSource, eventMapper)
    }

    @Test
    @Throws(Exception::class)
    fun `Given local data, When get all events with refresh flag, Should delete local data, fetch remote data then insert them locally`() {

        // Given

        val type = 1
        val refresh = true
        val localItems = listOf(EventLocalModel(1, type, "name", "description", 1,
                "runningTime", "tagLine", "image", "thumbnail", "url", currentPrice = 0f, offerPrice = 0f, minimumPrice = 0f))
        val remoteItems = listOf(EventRemoteModel(1, type, "name", "description", 1,
                "runningTime", "tagLine", "image", "thumbnail", "url", currentPrice = 0f, offerPrice = 0f, minimumPrice = 0f))
        // Empty local data is expected due to deletion of local source
        `when`(eventLocalDataSource.findByType(type)).thenReturn(Completable.complete().toObservable())
        // Fetch remote data
        `when`(eventRemoteDataSource.findByType(type)).thenReturn(Observable.just(remoteItems))
        // Mapping
        `when`(eventMapper.toLocal(remoteItems)).thenReturn(localItems)

        // When

        val testObserver = eventRepository.findByType(type, refresh)
                .test().await()

        // Should

        testObserver
                .assertComplete()
                .assertNoErrors()
                .assertValue(localItems)

        val inOrder = inOrder(eventLocalDataSource)
        // Delete local source by type
        inOrder.verify(eventLocalDataSource).deleteByType(type)
        // Call both data sources
        verify(eventLocalDataSource).findByType(type)
        verify(eventRemoteDataSource).findByType(type)
        // Insert into local source
        inOrder.verify(eventLocalDataSource).insertAll(localItems)
    }

}