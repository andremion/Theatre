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

package com.andremion.data.remote

import com.andremion.data.remote.api.TheatreApi
import com.andremion.data.remote.api.TheatreService
import com.andremion.data.remote.model.EventTypeRemoteModel
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@Suppress("IllegalIdentifier")
@RunWith(JUnit4::class)
/**
 * Enable the option to mock final classes is still a bit experimental, and requires a manual activation.
 * @see https://antonioleiva.com/mockito-2-kotlin/
 */
class EventTypeRemoteDataSourceTest {

    private lateinit var mockWebServer: MockWebServer

    private lateinit var theatreService: TheatreService

    private lateinit var eventTypeRemoteDataSource: EventTypeRemoteDataSource

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        theatreService = TheatreApi(mockWebServer.url("/").toString())

        eventTypeRemoteDataSource = EventTypeRemoteDataSource(theatreService)
    }

    @After
    fun stopServer() {
        mockWebServer.shutdown()
    }

    @Test
    @Throws(Exception::class)
    fun `Given event type data, When get all, Should get those event types`() {

        // Given

        val items = listOf(EventTypeRemoteModel(1, "name"))
        val json = """
        {
            "EventTypes": [
                {
                    "EventTypeId": 1,
                    "EventTypeName": "name"
                }
            ]
        }
        """

        val response = MockResponse().setBody(json)
        mockWebServer.enqueue(response)

        // When

        val testObserver = eventTypeRemoteDataSource.getAll()
                .test()

        // Then

        testObserver
                .assertComplete()
                .assertNoErrors()
                .assertValue(items)
    }

}