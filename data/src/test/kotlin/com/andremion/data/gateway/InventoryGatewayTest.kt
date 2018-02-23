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

package com.andremion.data.gateway

import com.andremion.data.local.model.EventLocalModel
import com.andremion.data.repository.EventRepository
import com.andremion.data.repository.EventTypeRepository
import com.andremion.data.repository.RatingRepository
import com.andremion.data.repository.VenueRepository
import com.andremion.domain.entity.Event
import com.andremion.domain.gateway.InventoryGateway
import io.reactivex.Observable
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
class InventoryGatewayTest {

    @Mock
    private lateinit var eventTypeRepository: EventTypeRepository
    @Mock
    private lateinit var eventRepository: EventRepository
    @Mock
    private lateinit var venueRepository: VenueRepository
    @Mock
    private lateinit var ratingRepository: RatingRepository

    private lateinit var inventoryGateway: InventoryGateway

    @Before
    fun setUp() {
        inventoryGateway = InventoryGatewayImpl(eventTypeRepository, eventRepository, venueRepository, ratingRepository)
    }

    @Test
    @Throws(Exception::class)
    fun `Given event items data, When get events, Should fetch data from repository then parse to domain data`() {

        // Given

        val type = 1
        val domainItems = listOf(Event(1, type, "name", "description", 1,
                "runningTime", "tagLine", "image", "thumbnail", "url", currentPrice = 0f, offerPrice = 0f, minimumPrice = 0f))
        val repositoryItems = listOf(EventLocalModel(1, type, "name", "description", 1,
                "runningTime", "tagLine", "image", "thumbnail", "url", currentPrice = 0f, offerPrice = 0f, minimumPrice = 0f))
        `when`(eventRepository.findByType(type)).thenReturn(Observable.just(repositoryItems))

        // When

        val testObserver = inventoryGateway.findEventsByType(type)
                .test()

        // Should

        testObserver
                .assertComplete()
                .assertNoErrors()
                .assertValue(domainItems)
    }
}