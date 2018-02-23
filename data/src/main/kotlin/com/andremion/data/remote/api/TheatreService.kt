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

import com.andremion.data.remote.model.EventRemoteModel
import com.andremion.data.remote.model.EventTypeRemoteModel
import com.andremion.data.remote.model.RatingRemoteModel
import com.andremion.data.remote.model.VenueRemoteModel
import com.serjltt.moshi.adapters.Wrapped
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheatreService {

    @GET("System/EventTypes")
    @Wrapped(path = ["EventTypes"])
    fun getEventTypes(): Observable<List<EventTypeRemoteModel>>

    @GET("Events")
    @Wrapped(path = ["Events"])
    fun getEvents(@Query("type") type: Int): Observable<List<EventRemoteModel>>

    @GET("Events/{id}")
    @Wrapped(path = ["Event"])
    fun getEvent(@Path("id") id: Int): Observable<EventRemoteModel>

    @GET("Venues/{id}")
    @Wrapped(path = ["Venue"])
    fun getVenue(@Path("id") id: Int): Observable<VenueRemoteModel>

    @GET("Events/{event}/Reviews")
    fun getEventRating(@Path("event") event: Int): Observable<RatingRemoteModel>
}
