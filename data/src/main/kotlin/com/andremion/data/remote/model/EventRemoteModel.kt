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

package com.andremion.data.remote.model

import com.squareup.moshi.Json

data class EventRemoteModel(
        @Json(name = "EventId") var id: Int,
        @Json(name = "EventType") var type: Int,
        @Json(name = "Name") var name: String,
        @Json(name = "Description") var description: String?,
        @Json(name = "VenueId") var venue: Int,
        @Json(name = "RunningTime") var runningTime: String?,
        @Json(name = "TagLine") var tagLine: String?,
        @Json(name = "MainImageUrl") var image: String?,
        @Json(name = "SmallImageUrl") var thumbnail: String?,
        @Json(name = "EventDetailUrl") var url: String?,
        @Json(name = "CurrentPrice") var currentPrice: Float,
        @Json(name = "OfferPrice") var offerPrice: Float,
        @Json(name = "EventMinimumPrice") var minimumPrice: Float
)