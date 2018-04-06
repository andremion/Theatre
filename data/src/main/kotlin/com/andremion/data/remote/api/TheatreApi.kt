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

package com.andremion.data.remote.api

import com.andremion.data.BuildConfig
import com.andremion.data.remote.api.util.AuthenticatorInterceptor
import com.andremion.data.remote.api.util.MoshiConverters
import com.andremion.data.remote.api.util.RetryAfterInterceptor
import com.andremion.data.remote.model.EventRemoteModel
import com.andremion.data.remote.model.EventTypeRemoteModel
import com.andremion.data.remote.model.RatingRemoteModel
import com.andremion.data.remote.model.VenueRemoteModel
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class TheatreApi(baseUrl: String) : TheatreService {

    companion object {
        private const val TIMEOUT = 10L
    }

    private val service: TheatreService

    init {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) BODY else BASIC

        val httpClient = OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(AuthenticatorInterceptor())
                .addInterceptor(RetryAfterInterceptor())

        val client = httpClient.build()

        val moshi = Moshi.Builder()
                .add(Wrapped.ADAPTER_FACTORY)
                .add(MoshiConverters())
                .add(KotlinJsonAdapterFactory())
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()

        service = retrofit.create(TheatreService::class.java)
    }

    override fun getEventTypes(): Observable<List<EventTypeRemoteModel>> = service.getEventTypes()

    override fun getEvents(type: Int): Observable<List<EventRemoteModel>> = service.getEvents(type)

    override fun getEvent(id: Int): Observable<EventRemoteModel> = service.getEvent(id)

    override fun getVenue(id: Int): Observable<VenueRemoteModel> = service.getVenue(id)

    override fun getEventRating(event: Int): Observable<RatingRemoteModel> = service.getEventRating(event)
}
