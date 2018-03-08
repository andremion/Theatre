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

package com.andremion.theatre.internal.injection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.andremion.domain.interactor.*
import com.andremion.theatre.event.detail.EventDetailViewModel
import com.andremion.theatre.event.detail.rating.EventRatingViewModel
import com.andremion.theatre.event.list.EventListViewModel
import com.andremion.theatre.event.type.EventTypeViewModel
import com.andremion.theatre.startup.StartupViewModel

class ViewModelFactory(private val context: Context,
                       private val eventTypeGetAllUseCase: EventTypeGetAllUseCase,
                       private val eventFindByTypeUseCase: EventFindByTypeUseCase,
                       private val eventGetByIdUseCase: EventGetByIdUseCase,
                       private val venueGetByIdUseCase: VenueGetByIdUseCase,
                       private val ratingFindByEventUseCase: RatingFindByEventUseCase) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
            when {
                modelClass.isAssignableFrom(StartupViewModel::class.java) ->
                    StartupViewModel(context, eventTypeGetAllUseCase) as T

                modelClass.isAssignableFrom(EventTypeViewModel::class.java) ->
                    EventTypeViewModel(context, eventTypeGetAllUseCase) as T

                modelClass.isAssignableFrom(EventListViewModel::class.java) ->
                    EventListViewModel(context, eventFindByTypeUseCase) as T

                modelClass.isAssignableFrom(EventDetailViewModel::class.java) ->
                    EventDetailViewModel(context, eventGetByIdUseCase, venueGetByIdUseCase) as T

                modelClass.isAssignableFrom(EventRatingViewModel::class.java) ->
                    EventRatingViewModel(context, ratingFindByEventUseCase) as T

                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
}