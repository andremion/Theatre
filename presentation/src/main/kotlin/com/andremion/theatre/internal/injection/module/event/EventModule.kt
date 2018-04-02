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

package com.andremion.theatre.internal.injection.module.event

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.andremion.domain.Schedulers
import com.andremion.domain.gateway.InventoryGateway
import com.andremion.domain.interactor.EventGetByIdUseCase
import com.andremion.domain.interactor.RatingFindByEventUseCase
import com.andremion.domain.interactor.VenueGetByIdUseCase
import com.andremion.theatre.event.detail.EventDescriptionFragment
import com.andremion.theatre.event.detail.EventDetailViewModel
import com.andremion.theatre.event.detail.rating.EventRatingViewModel
import com.andremion.theatre.event.detail.rating.ReviewListFragment
import com.andremion.theatre.internal.injection.scope.EventScope
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class EventModule {

    @ContributesAndroidInjector
    internal abstract fun contributeEventDescriptionFragment(): EventDescriptionFragment

    @ContributesAndroidInjector
    internal abstract fun contributeReviewListFragment(): ReviewListFragment

    @Module
    companion object {

        @EventScope
        @Provides
        @JvmStatic
        internal fun provideEventGetByIdUseCase(schedulers: Schedulers, inventoryGateway: InventoryGateway): EventGetByIdUseCase {
            return EventGetByIdUseCase(schedulers, inventoryGateway)
        }

        @EventScope
        @Provides
        @JvmStatic
        internal fun provideVenueGetByIdUseCase(schedulers: Schedulers, inventoryGateway: InventoryGateway): VenueGetByIdUseCase {
            return VenueGetByIdUseCase(schedulers, inventoryGateway)
        }

        @EventScope
        @Provides
        @JvmStatic
        internal fun provideRatingFindByEventUseCase(schedulers: Schedulers, inventoryGateway: InventoryGateway): RatingFindByEventUseCase {
            return RatingFindByEventUseCase(schedulers, inventoryGateway)
        }

        @EventScope
        @Provides
        @JvmStatic
        internal fun provideViewModelFactory(context: Context,
                                             eventGetByIdUseCase: EventGetByIdUseCase,
                                             venueGetByIdUseCase: VenueGetByIdUseCase,
                                             ratingFindByEventUseCase: RatingFindByEventUseCase): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return when {
                        modelClass.isAssignableFrom(EventDetailViewModel::class.java) ->
                            EventDetailViewModel(context, eventGetByIdUseCase, venueGetByIdUseCase) as T

                        modelClass.isAssignableFrom(EventRatingViewModel::class.java) ->
                            EventRatingViewModel(context, ratingFindByEventUseCase) as T

                        else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                    }
                }
            }
        }
    }

}