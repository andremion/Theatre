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

import com.andremion.domain.MissingUseCaseParameterException
import com.andremion.domain.Schedulers
import com.andremion.domain.UseCase
import com.andremion.domain.entity.Event
import com.andremion.domain.gateway.InventoryGateway
import io.reactivex.Observable

class EventFindByTypeUseCase(schedulers: Schedulers, private val inventoryGateway: InventoryGateway) :
        UseCase<Pair<Int, Boolean>, List<Event>>(schedulers) {

    override fun buildObservable(params: Pair<Int, Boolean>?): Observable<List<Event>> {
        if (params == null) throw MissingUseCaseParameterException(javaClass)
        val (type, refresh) = params
        return inventoryGateway.findEventsByType(type, refresh)
    }
}