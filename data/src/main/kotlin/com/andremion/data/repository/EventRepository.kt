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
import com.andremion.data.repository.mapper.EventMapper
import io.reactivex.Observable

class EventRepository(
        private val eventLocalDataSource: EventLocalDataSource,
        private val eventRemoteDataSource: EventRemoteDataSource,
        private val eventMapper: EventMapper) {

    fun findByType(type: Int, refresh: Boolean = false): Observable<List<EventLocalModel>> {

        val local = eventLocalDataSource.findByType(type)
                .filter { !it.isEmpty() }

        val remote = eventRemoteDataSource.findByType(type)
                .map { eventMapper.toLocal(it) }
                .doOnNext { eventLocalDataSource.insertAll(it) }

        return Observable.just(refresh)
                .doOnNext { if (it) eventLocalDataSource.deleteByType(type) }
                .flatMap {
                    Observable.concat(local, remote)
                            .firstElement()
                            .toObservable()
                }
    }

    fun getById(id: Int): Observable<EventLocalModel> {

        val local = eventLocalDataSource.getById(id)

        val remote = eventRemoteDataSource.getById(id)
                .map { eventMapper.toLocal(it) }
                .doOnNext { eventLocalDataSource.insert(it) }

        return Observable.concat(local, remote)
                .firstElement()
                .toObservable()
    }
}
