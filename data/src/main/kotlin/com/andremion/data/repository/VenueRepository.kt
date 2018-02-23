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

import com.andremion.data.local.VenueLocalDataSource
import com.andremion.data.local.model.VenueLocalModel
import com.andremion.data.remote.VenueRemoteDataSource
import com.andremion.data.repository.mapper.VenueMapper
import io.reactivex.Observable

class VenueRepository(
        private val venueLocalDataSource: VenueLocalDataSource,
        private val venueRemoteDataSource: VenueRemoteDataSource,
        private val venueMapper: VenueMapper) {

    fun getById(id: Int): Observable<VenueLocalModel> {

        val local = venueLocalDataSource.getById(id)

        val remote = venueRemoteDataSource.getById(id)
                .map { venueMapper.toLocal(it) }
                .doOnNext { venueLocalDataSource.insert(it) }

        return Observable.concat(local, remote)
                .firstElement()
                .toObservable()
    }
}
