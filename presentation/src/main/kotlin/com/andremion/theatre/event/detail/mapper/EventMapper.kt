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

package com.andremion.theatre.event.detail.mapper

import android.content.Context
import com.andremion.domain.entity.Event
import com.andremion.theatre.R
import com.andremion.theatre.event.detail.model.EventModel

class EventMapper(private val context: Context) {

    fun toModel(event: Event): EventModel {
        return EventModel(event.id, event.type.toString(), event.name, event.description ?: "", event.venue.toString(),
                event.tagLine ?: "", event.image ?: "", event.thumbnail ?: "", event.url ?: "", formatPrice(event))
    }

    private fun formatPrice(event: Event): String {
        return if (event.offerPrice > 0 && event.currentPrice > 0) {
            context.getString(R.string.offer_price, event.currentPrice - event.offerPrice)
        } else if (event.minimumPrice > 0) {
            context.getString(R.string.minimum_price, event.minimumPrice)
        } else {
            ""
        }
    }
}