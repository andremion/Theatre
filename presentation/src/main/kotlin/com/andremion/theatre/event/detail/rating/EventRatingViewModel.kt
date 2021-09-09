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

package com.andremion.theatre.event.detail.rating

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.andremion.domain.entity.Rating
import com.andremion.domain.interactor.RatingFindByEventUseCase
import com.andremion.theatre.R
import com.andremion.theatre.internal.util.BaseAndroidViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

class EventRatingViewModel(context: Context, private val ratingFindByEventUseCase: RatingFindByEventUseCase)
    : BaseAndroidViewModel(context.applicationContext as Application) {

    val loading = ObservableBoolean()
    val result = ObservableField<Rating>()
    val error = ObservableField<String>()
    val empty = ObservableBoolean()

    private var event = 0

    fun loadEventRating(event: Int, refresh: Boolean = false) {
        this.event = event
        addDisposable(getRatingByEvent(event, refresh))
    }

    fun refresh() = loadEventRating(event, true)

    private fun getRatingByEvent(event: Int, refresh: Boolean): Disposable {
        val params = Pair(event, refresh)
        return ratingFindByEventUseCase.execute(params)
                .subscribeWith(object : DisposableObserver<Rating>() {

                    override fun onStart() {
                        loading.set(true)
                        empty.set(false)
                    }

                    override fun onNext(t: Rating) {
                        loading.set(false)
                        result.set(t)
                        empty.set(t.reviews.isEmpty())
                    }

                    override fun onError(t: Throwable) {
                        loading.set(false)
                        error.set(t.localizedMessage ?: t.message ?: context.getString(R.string.unknown_error))
                    }

                    override fun onComplete() {
                        // no-op
                    }
                })
    }

}