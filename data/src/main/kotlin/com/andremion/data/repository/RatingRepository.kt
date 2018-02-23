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

import com.andremion.data.local.RatingLocalDataSource
import com.andremion.data.local.model.RatingAndReviewLocalModel
import com.andremion.data.remote.RatingRemoteDataSource
import com.andremion.data.repository.mapper.RatingMapper
import io.reactivex.Observable

class RatingRepository(
        private val ratingLocalDataSource: RatingLocalDataSource,
        private val ratingRemoteDataSource: RatingRemoteDataSource,
        private val ratingMapper: RatingMapper) {

    fun findByEvent(event: Int, refresh: Boolean = false): Observable<RatingAndReviewLocalModel> {

        val local = ratingLocalDataSource.findByEvent(event)

        val remote = ratingRemoteDataSource.findByEvent(event)
                .map { ratingMapper.toLocal(event, it) }
                .doOnNext { ratingLocalDataSource.insert(it.rating, it.reviews) }

        return Observable.just(refresh)
                .doOnNext { if (it) ratingLocalDataSource.deleteByEvent(event) }
                .flatMap {
                    Observable.concat(local, remote)
                            .firstElement()
                            .toObservable()
                }
    }
}
