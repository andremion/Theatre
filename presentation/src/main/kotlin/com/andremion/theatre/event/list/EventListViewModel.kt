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

package com.andremion.theatre.event.list

import android.app.Application
import android.content.Context
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.andremion.domain.entity.Event
import com.andremion.domain.interactor.EventFindByTypeUseCase
import com.andremion.theatre.R
import com.andremion.theatre.event.list.mapper.EventMapper
import com.andremion.theatre.event.list.model.EventModel
import com.andremion.theatre.internal.util.BaseAndroidViewModel
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

class EventListViewModel(context: Context, private val eventFindByTypeUseCase: EventFindByTypeUseCase)
    : BaseAndroidViewModel(context.applicationContext as Application) {

    private val mapper = EventMapper(context)

    val loading = ObservableBoolean()
    val result = ObservableArrayList<EventModel>()
    val error = ObservableField<String>()
    val empty = ObservableBoolean()

    private var type = 0

    fun loadEventList(type: Int, refresh: Boolean = false) {
        this.type = type
        addDisposable(findEventByType(type, refresh))
    }

    fun refresh() = loadEventList(type, true)

    private fun findEventByType(type: Int, refresh: Boolean): Disposable {
        val params = Pair(type, refresh)
        return eventFindByTypeUseCase.execute(params)
                .subscribeWith(object : DisposableObserver<List<Event>>() {

                    override fun onStart() {
                        loading.set(true)
                        empty.set(false)
                    }

                    override fun onNext(t: List<Event>) {
                        loading.set(false)
                        result.clear()
                        result.addAll(t.map { mapper.toModel(it) })
                        empty.set(t.isEmpty())
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
