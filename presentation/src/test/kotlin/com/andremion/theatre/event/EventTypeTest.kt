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

package com.andremion.theatre.event

import android.app.Application
import android.content.Context
import com.andremion.domain.entity.EventType
import com.andremion.domain.interactor.EventTypeGetAllUseCase
import com.andremion.theatre.event.type.EventTypeViewModel
import io.reactivex.Observable
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@Suppress("IllegalIdentifier")
@RunWith(MockitoJUnitRunner::class)
/**
 * Enable the option to mock final classes is still a bit experimental, and requires a manual activation.
 * @see https://antonioleiva.com/mockito-2-kotlin/
 */
class EventTypeTest {

    @Mock
    private lateinit var context: Context
    @Mock
    private lateinit var application: Application
    @Mock
    private lateinit var eventTypeGetAllUseCase: EventTypeGetAllUseCase

    private lateinit var eventTypeViewModel: EventTypeViewModel

    @Before
    fun setup() {
        `when`(context.applicationContext).thenReturn(application)
        eventTypeViewModel = EventTypeViewModel(context, eventTypeGetAllUseCase)
    }

    @Test
    @Throws(Exception::class)
    fun `Given event type items, When load event types, Should update result`() {

        // Given

        val items = listOf(EventType(1, "name"))

        `when`(eventTypeGetAllUseCase.execute()).thenReturn(Observable.just(items))

        // When

        eventTypeViewModel.loadEventTypeList()

        // Should

        assertThat(eventTypeViewModel.result, `is`(items))
    }

    @Test
    @Throws(Exception::class)
    fun `Given error emission, When load event types with error, Should update error`() {

        // Given

        val error = RuntimeException("Error emission")
        `when`(eventTypeGetAllUseCase.execute()).thenReturn(Observable.error(error))

        // When

        eventTypeViewModel.loadEventTypeList()

        // Should

        assertThat(eventTypeViewModel.error.get(), `is`(error.message))
    }

    @Test
    @Throws(Exception::class)
    fun `Given unknown error emission, When load event types with unknown error, Should update error`() {

        // Given

        val error = "Unknown error"
        `when`(application.getString(anyInt())).thenReturn(error)
        `when`(eventTypeGetAllUseCase.execute()).thenReturn(Observable.error(RuntimeException()))

        // When

        eventTypeViewModel.loadEventTypeList()

        // Should

        assertThat(eventTypeViewModel.error.get(), `is`(error))
    }
}
