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

package com.andremion.theatre.internal.injection.module

import android.content.Context
import com.andremion.domain.interactor.*
import com.andremion.theatre.internal.injection.ViewModelFactory
import com.andremion.theatre.navigation.Navigator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class PresentationModule {

    @Provides
    @Singleton
    internal fun provideNavigator() = Navigator()

    @Provides
    @Singleton
    internal fun provideViewModelFactory(context: Context,
                                         eventTypeGetAllUseCase: EventTypeGetAllUseCase,
                                         eventFindByTypeUseCase: EventFindByTypeUseCase,
                                         eventGetByIdUseCase: EventGetByIdUseCase,
                                         venueGetByIdUseCase: VenueGetByIdUseCase,
                                         ratingFindByEventUseCase: RatingFindByEventUseCase): ViewModelFactory {
        return ViewModelFactory(
                context,
                eventTypeGetAllUseCase,
                eventFindByTypeUseCase,
                eventGetByIdUseCase,
                venueGetByIdUseCase,
                ratingFindByEventUseCase)
    }
}
