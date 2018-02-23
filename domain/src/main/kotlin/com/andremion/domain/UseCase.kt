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

package com.andremion.domain

import io.reactivex.Observable

/**
 * A base class for an use case that will be executed by presentation layer
 */
abstract class UseCase<in Params, Result> internal constructor(private val schedulers: Schedulers) {

    internal abstract fun buildObservable(params: Params?): Observable<Result>

    fun execute(params: Params? = null): Observable<Result> {
        return buildObservable(params)
                .subscribeOn(schedulers.subscribeOn)
                // Unfortunately RxJava had a bug that if any Exceptions were being thrown later
                // in the stream they would incorrectly cut ahead of the successful emissions
                // and break the flow.
                // In order to fix this, an overload was added in version 1.1.1
                // for observeOn(Scheduler scheduler, boolean delayError)
                // in order to signal the Scheduler to respect the delaying of errors.
                // https://medium.com/yammer-engineering/chaining-multiple-sources-with-rxjava-20eb6850e5d9
                .observeOn(schedulers.observeOn, true)
    }
}
