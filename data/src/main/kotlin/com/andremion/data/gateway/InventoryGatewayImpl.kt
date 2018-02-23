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

import com.andremion.data.gateway.mapper.InventoryMapper
import com.andremion.data.repository.EventRepository
import com.andremion.data.repository.EventTypeRepository
import com.andremion.data.repository.RatingRepository
import com.andremion.data.repository.VenueRepository
import com.andremion.domain.entity.Event
import com.andremion.domain.entity.Rating
import com.andremion.domain.entity.Venue
import com.andremion.domain.gateway.InventoryGateway
import io.reactivex.Observable

class InventoryGatewayImpl(
        private val eventTypeRepository: EventTypeRepository,
        private val eventRepository: EventRepository,
        private val venueRepository: VenueRepository,
        private val ratingRepository: RatingRepository) : InventoryGateway {

    private val mapper = InventoryMapper()

    override fun findEventsByType(type: Int, refresh: Boolean): Observable<List<Event>> =

            eventRepository.findByType(type, refresh)
                    .doOnError { println("Event By Type($type) Error") }
                    .map { it.map { mapper.toEntity(it) } }

    override fun getVenueById(id: Int): Observable<Venue> =

            venueRepository.getById(id)
                    .doOnError { println("Venue By Id($id) Error") }
                    .map { mapper.toEntity(it) }

    override fun getEventById(id: Int): Observable<Event> =

            eventRepository.getById(id)
                    .doOnError { println("Event By Id($id) Error") }
                    .flatMap { event ->
                        eventTypeRepository.getById(event.type)
                                .doOnError { println("Event Type By Id(${event.type}) Error") }
                                .map { mapper.toEntity(event) }
                    }

    override fun findRatingByEvent(event: Int, refresh: Boolean): Observable<Rating> =

            ratingRepository.findByEvent(event, refresh)
                    .doOnError { println("Rating By Event($event) Error") }
                    .map { mapper.toEntity(it) }
}
