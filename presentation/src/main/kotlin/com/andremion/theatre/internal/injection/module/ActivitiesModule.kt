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

import com.andremion.theatre.event.detail.EventActivity
import com.andremion.theatre.home.HomeActivity
import com.andremion.theatre.internal.injection.module.event.EventModule
import com.andremion.theatre.internal.injection.module.home.HomeModule
import com.andremion.theatre.internal.injection.module.startup.StartupModule
import com.andremion.theatre.internal.injection.scope.EventScope
import com.andremion.theatre.internal.injection.scope.HomeScope
import com.andremion.theatre.internal.injection.scope.StartupScope
import com.andremion.theatre.startup.StartupActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivitiesModule {

    @StartupScope
    @ContributesAndroidInjector(modules = [StartupModule::class])
    internal abstract fun contributeStartupActivity(): StartupActivity

    @HomeScope
    @ContributesAndroidInjector(modules = [HomeModule::class])
    internal abstract fun contributeHomeActivity(): HomeActivity

    @EventScope
    @ContributesAndroidInjector(modules = [EventModule::class])
    internal abstract fun contributeEventActivity(): EventActivity
}