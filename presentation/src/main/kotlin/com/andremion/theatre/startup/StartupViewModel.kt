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

package com.andremion.theatre.startup

import android.app.Application
import android.content.Context
import com.andremion.domain.entity.EventType
import com.andremion.domain.interactor.EventTypeGetAllUseCase
import com.andremion.theatre.R
import com.andremion.theatre.internal.util.BaseAndroidViewModel
import com.andremion.theatre.internal.util.SingleLiveData
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

class StartupViewModel(context: Context, private val eventTypeGetAllUseCase: EventTypeGetAllUseCase)
    : BaseAndroidViewModel(context.applicationContext as Application) {

    private val _result = SingleLiveData<Boolean>()
    val result = _result
    private val _error = SingleLiveData<String>()
    val error = _error

    fun startup() = addDisposable(getAllEventTypes())

    private fun getAllEventTypes(): Disposable {
        return eventTypeGetAllUseCase.execute()
                .subscribeWith(object : DisposableObserver<List<EventType>>() {

                    override fun onNext(t: List<EventType>) {
                        result.value = true
                    }

                    override fun onError(t: Throwable) {
                        error.value = t.localizedMessage ?: t.message ?: context.getString(R.string.unknown_error)
                    }

                    override fun onComplete() {
                        // no-op
                    }
                })
    }
}
