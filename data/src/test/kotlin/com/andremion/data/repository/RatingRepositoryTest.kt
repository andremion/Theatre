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
import com.andremion.data.local.model.RatingLocalModel
import com.andremion.data.local.model.ReviewLocalModel
import com.andremion.data.remote.RatingRemoteDataSource
import com.andremion.data.remote.model.RatingRemoteModel
import com.andremion.data.remote.model.ReviewRemoteModel
import com.andremion.data.repository.mapper.RatingMapper
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@Suppress("IllegalIdentifier")
@RunWith(MockitoJUnitRunner::class)
/**
 * Enable the option to mock final classes is still a bit experimental, and requires a manual activation.
 * @see https://antonioleiva.com/mockito-2-kotlin/
 */
class RatingRepositoryTest {

    @Mock
    private lateinit var ratingLocalDataSource: RatingLocalDataSource
    @Mock
    private lateinit var ratingRemoteDataSource: RatingRemoteDataSource
    @Mock
    private lateinit var ratingMapper: RatingMapper

    private lateinit var ratingRepository: RatingRepository

    @Before
    fun setUp() {
        ratingRepository = RatingRepository(ratingLocalDataSource, ratingRemoteDataSource, ratingMapper)
    }

    @Test
    @Throws(Exception::class)
    fun `Given local data, When get all ratings with refresh flag, Should delete local data, fetch remote data then insert them locally`() {

        // Given

        val event = 1
        val refresh = true

        val localItems = listOf(ReviewLocalModel(event, "consumer", 4, Date(), "content"))
        val localRating = RatingLocalModel(event, 4f)
        val localRatingAndReview = RatingAndReviewLocalModel(localRating).apply {
            reviews = localItems
        }
        val remoteItems = listOf(ReviewRemoteModel("consumer", 4, Date(), "content"))
        val remoteRating = RatingRemoteModel(4f, remoteItems)

        // Empty local data is expected due to deletion of local source
        `when`(ratingLocalDataSource.findByEvent(event)).thenReturn(Completable.complete().toObservable())
        // Fetch remote data
        `when`(ratingRemoteDataSource.findByEvent(event)).thenReturn(Observable.just(remoteRating))
        // Mapping
        `when`(ratingMapper.toLocal(event, remoteRating)).thenReturn(localRatingAndReview)

        // When

        val testObserver = ratingRepository.findByEvent(event, refresh)
                .test().await()

        // Should

        testObserver
                .assertComplete()
                .assertNoErrors()
                .assertValue(localRatingAndReview)

        val inOrder = inOrder(ratingLocalDataSource)
        // Delete local source by event
        inOrder.verify(ratingLocalDataSource).deleteByEvent(event)
        // Call both data sources
        verify(ratingLocalDataSource).findByEvent(event)
        verify(ratingRemoteDataSource).findByEvent(event)
        // Insert into local source
        inOrder.verify(ratingLocalDataSource).insert(localRating, localItems)
    }

}
