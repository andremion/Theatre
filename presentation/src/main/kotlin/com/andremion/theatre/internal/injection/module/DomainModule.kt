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

import com.andremion.domain.Schedulers
import com.andremion.domain.gateway.InventoryGateway
import com.andremion.domain.gateway.SystemGateway
import com.andremion.domain.interactor.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class DomainModule {

    @Provides
    @Singleton
    internal fun provideEventTypeGetAllUseCase(schedulers: Schedulers, systemGateway: SystemGateway): EventTypeGetAllUseCase {
        return EventTypeGetAllUseCase(schedulers, systemGateway)
    }

    @Provides
    @Singleton
    internal fun provideEventFindByTypeUseCase(schedulers: Schedulers, inventoryGateway: InventoryGateway): EventFindByTypeUseCase {
        return EventFindByTypeUseCase(schedulers, inventoryGateway)
    }

    @Provides
    @Singleton
    internal fun provideEventGetByIdUseCase(schedulers: Schedulers, inventoryGateway: InventoryGateway): EventGetByIdUseCase {
        return EventGetByIdUseCase(schedulers, inventoryGateway)
    }

    @Provides
    @Singleton
    internal fun provideVenueGetByIdUseCase(schedulers: Schedulers, inventoryGateway: InventoryGateway): VenueGetByIdUseCase {
        return VenueGetByIdUseCase(schedulers, inventoryGateway)
    }

    @Provides
    @Singleton
    internal fun provideRatingFindByEventUseCase(schedulers: Schedulers, inventoryGateway: InventoryGateway): RatingFindByEventUseCase {
        return RatingFindByEventUseCase(schedulers, inventoryGateway)
    }
}
