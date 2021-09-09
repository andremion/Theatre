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

package com.andremion.theatre.event.detail

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.andremion.domain.entity.Event
import com.andremion.domain.entity.Venue
import com.andremion.domain.interactor.EventGetByIdUseCase
import com.andremion.domain.interactor.VenueGetByIdUseCase
import com.andremion.theatre.R
import com.andremion.theatre.event.detail.mapper.EventMapper
import com.andremion.theatre.event.detail.model.EventModel
import com.andremion.theatre.internal.util.BaseAndroidViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver


class EventDetailViewModel(context: Context,
                           private val eventGetByIdUseCase: EventGetByIdUseCase,
                           private val venueGetByIdUseCase: VenueGetByIdUseCase)
    : BaseAndroidViewModel(context.applicationContext as Application) {

    private val mapper = EventMapper(context)

    val loading = ObservableBoolean()
    val event = ObservableField<EventModel>()
    val venue = ObservableField<Venue>()
    val error = ObservableField<String>()

    fun loadEventDetail(id: Int) = addDisposable(getEventById(id))

    private fun getEventById(id: Int): Disposable {
        return eventGetByIdUseCase.execute(id)
                .subscribeWith(object : DisposableObserver<Event>() {

                    override fun onStart() {
                        loading.set(true)
                    }

                    override fun onNext(result: Event) {
                        loading.set(false)
                        event.set(mapper.toModel(result))

                        addDisposable(getVenueById(result))
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

    private fun getVenueById(result: Event): Disposable {
        return venueGetByIdUseCase.execute(result.venue)
                .subscribeWith(object : DisposableObserver<Venue>() {
                    override fun onNext(result: Venue) {
                        venue.set(result)
                    }

                    override fun onError(t: Throwable) {
                        error.set(t.localizedMessage ?: t.message ?: context.getString(R.string.unknown_error))
                    }

                    override fun onComplete() {
                        // no-op
                    }
                })
    }
}
