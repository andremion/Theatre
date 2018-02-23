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
import com.andremion.data.repository.mapper.EventTypeMapper
import io.reactivex.Observable

class EventTypeRepository(
        private val eventTypeLocalDataSource: EventTypeLocalDataSource,
        private val eventTypeRemoteDataSource: EventTypeRemoteDataSource,
        private val eventTypeMapper: EventTypeMapper) {

    fun getAll(): Observable<List<EventTypeLocalModel>> {

        val local = eventTypeLocalDataSource.getAll()
                .filter { !it.isEmpty() }

        val remote = eventTypeRemoteDataSource.getAll()
                .map { eventTypeMapper.toLocal(it) }
                .doOnNext { eventTypeLocalDataSource.insertAll(it) }

        return Observable.concat(local, remote)
                .firstElement()
                .toObservable()
    }

    fun getById(id: Int): Observable<EventTypeLocalModel> = eventTypeLocalDataSource.getById(id)
}
