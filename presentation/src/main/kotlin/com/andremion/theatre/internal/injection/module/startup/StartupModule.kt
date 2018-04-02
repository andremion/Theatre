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

package com.andremion.theatre.internal.injection.module.startup

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.andremion.domain.Schedulers
import com.andremion.domain.gateway.SystemGateway
import com.andremion.domain.interactor.EventTypeGetAllUseCase
import com.andremion.theatre.event.type.EventTypeViewModel
import com.andremion.theatre.internal.injection.scope.StartupScope
import com.andremion.theatre.startup.StartupViewModel
import dagger.Module
import dagger.Provides

@Module
internal class StartupModule {

    @StartupScope
    @Provides
    internal fun provideEventTypeGetAllUseCase(schedulers: Schedulers, systemGateway: SystemGateway): EventTypeGetAllUseCase {
        return EventTypeGetAllUseCase(schedulers, systemGateway)
    }

    @Provides
    internal fun provideViewModelFactory(context: Context,
                                         eventTypeGetAllUseCase: EventTypeGetAllUseCase): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return when {
                    modelClass.isAssignableFrom(StartupViewModel::class.java) ->
                        StartupViewModel(context, eventTypeGetAllUseCase) as T

                    modelClass.isAssignableFrom(EventTypeViewModel::class.java) ->
                        EventTypeViewModel(context, eventTypeGetAllUseCase) as T

                    else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            }
        }
    }

}