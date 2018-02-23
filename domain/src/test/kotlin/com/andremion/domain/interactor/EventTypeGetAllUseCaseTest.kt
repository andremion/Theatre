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

package com.andremion.domain.interactor

import com.andremion.domain.Schedulers
import com.andremion.domain.entity.EventType
import com.andremion.domain.gateway.SystemGateway
import com.andremion.domain.utils.TestSchedulers
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@Suppress("IllegalIdentifier")
@RunWith(MockitoJUnitRunner::class)
/**
 * Enable the option to mock final classes is still a bit experimental, and requires a manual activation.
 * @see https://antonioleiva.com/mockito-2-kotlin/
 */
class EventTypeGetAllUseCaseTest {

    @Mock
    private lateinit var systemGateway: SystemGateway

    private lateinit var schedulers: Schedulers
    private lateinit var eventTypeGetAllUseCase: EventTypeGetAllUseCase

    @Before
    fun setUp() {
        schedulers = TestSchedulers()
        eventTypeGetAllUseCase = EventTypeGetAllUseCase(schedulers, systemGateway)
    }

    @Test
    @Throws(Exception::class)
    fun `Given event type items data, When get event types, Should fetch data from system gateway`() {

        // Given

        val items = listOf(EventType(1, "name"))
        Mockito.`when`(systemGateway.getEventTypes()).thenReturn(Observable.just(items))

        // When

        val testObserver = eventTypeGetAllUseCase.execute()
                .test()

        // Should

        testObserver
                .assertComplete()
                .assertNoErrors()
                .assertValue(items)

        Mockito.verify(systemGateway).getEventTypes()
    }
}