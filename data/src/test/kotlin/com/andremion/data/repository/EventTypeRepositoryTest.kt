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

import com.andremion.data.local.EventTypeLocalDataSource
import com.andremion.data.local.model.EventTypeLocalModel
import com.andremion.data.remote.EventTypeRemoteDataSource
import com.andremion.data.remote.model.EventTypeRemoteModel
import com.andremion.data.repository.mapper.EventTypeMapper
import io.reactivex.Completable
import io.reactivex.Maybe
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
class EventTypeRepositoryTest {

    @Mock
    private lateinit var eventTypeLocalDataSource: EventTypeLocalDataSource
    @Mock
    private lateinit var eventTypeRemoteDataSource: EventTypeRemoteDataSource
    @Mock
    private lateinit var eventTypeMapper: EventTypeMapper

    private lateinit var eventTypeRepository: EventTypeRepository

    @Before
    fun setUp() {
        eventTypeRepository = EventTypeRepository(eventTypeLocalDataSource, eventTypeRemoteDataSource, eventTypeMapper)
    }

    @Test
    @Throws(Exception::class)
    fun `Given empty local data, When get all event Types, Should fetch remote data then insert them locally`() {

        // Given

        val localItems = listOf(EventTypeLocalModel(1, "name"))
        val remoteItems = listOf(EventTypeRemoteModel(1, "name"))
        // Empty local data
        `when`(eventTypeLocalDataSource.getAll()).thenReturn(Completable.complete().toObservable())
        // Fetch remote data
        `when`(eventTypeRemoteDataSource.getAll()).thenReturn(Observable.just(remoteItems))
        // Mapping
        `when`(eventTypeMapper.toLocal(remoteItems)).thenReturn(localItems)

        // When

        val testObserver = eventTypeRepository.getAll()
                .test().await()

        // Should

        testObserver
                .assertComplete()
                .assertNoErrors()
                .assertValue(localItems)

        // Call both data sources
        verify(eventTypeLocalDataSource).getAll()
        verify(eventTypeRemoteDataSource).getAll()
        // Insert into local source
        verify(eventTypeLocalDataSource).insertAll(localItems)
    }

    @Test
    @Throws(Exception::class)
    fun `Given local data, When get all event Types, Should fetch just from local data`() {

        // Given

        val localItems = listOf(EventTypeLocalModel(1, "name"))
        val remoteItems = listOf(EventTypeRemoteModel(1, "name"))
        // Fetch local data
        `when`(eventTypeLocalDataSource.getAll()).thenReturn(Maybe.just(localItems).toObservable())
        // Fetch remote data
        `when`(eventTypeRemoteDataSource.getAll()).thenReturn(Observable.just(remoteItems))

        // When

        val testObserver = eventTypeRepository.getAll()
                .test().await()

        // Should

        testObserver
                .assertComplete()
                .assertNoErrors()
                .assertValue(localItems)

        // Call both data sources
        verify(eventTypeLocalDataSource).getAll()
        verify(eventTypeRemoteDataSource).getAll()
        // Never insert into local source
        verify(eventTypeLocalDataSource, never()).insertAll(localItems)
    }

    @Test
    @Throws(Exception::class)
    fun `Given local data, When get all event Types and remote emits an error, Should not propagate the error`() {

        // Given

        val localItems = listOf(EventTypeLocalModel(1, "name"))
        // Fetch local data
        `when`(eventTypeLocalDataSource.getAll()).thenReturn(Maybe.just(localItems).toObservable())
        // Fetch remote data
        `when`(eventTypeRemoteDataSource.getAll()).thenReturn(Observable.error(RuntimeException()))

        // When

        val testObserver = eventTypeRepository.getAll()
                .test().await()

        // Should

        testObserver
                .assertComplete()
                .assertNoErrors()
                .assertValue(localItems)

        // Call both data sources
        verify(eventTypeLocalDataSource).getAll()
        verify(eventTypeRemoteDataSource).getAll()
        // Never insert into local source
        verify(eventTypeLocalDataSource, never()).insertAll(localItems)
    }
}