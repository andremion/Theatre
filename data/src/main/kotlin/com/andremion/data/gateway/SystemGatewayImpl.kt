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

import com.andremion.data.gateway.mapper.SystemMapper
import com.andremion.data.repository.EventTypeRepository
import com.andremion.domain.entity.EventType
import com.andremion.domain.gateway.SystemGateway
import io.reactivex.Observable

class SystemGatewayImpl(private val eventTypeRepository: EventTypeRepository) : SystemGateway {

    private val mapper = SystemMapper()

    override fun getEventTypes(): Observable<List<EventType>> =

            eventTypeRepository.getAll()
                    .doOnError { println("Event Type Error") }
                    .map { it.map { mapper.toEntity(it) } }
}
