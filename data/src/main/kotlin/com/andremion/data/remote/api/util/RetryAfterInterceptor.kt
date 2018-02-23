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

package com.andremion.data.remote.api.util

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.net.HttpURLConnection

/**
 * Retry the request in case of "Account Over Queries Per Second Limit" error
 */
class RetryAfterInterceptor : Interceptor {

    private val logger = HttpLoggingInterceptor.Logger.DEFAULT

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        var response = chain.proceed(request)

        if (response.code() == HttpURLConnection.HTTP_FORBIDDEN) {
            val retryAfter = parseRetryAfter(response)
            if (retryAfter != null) {
                logger.log("Retrying after $retryAfter seconds...")
                Thread.sleep((retryAfter * 1000).toLong())
                response = chain.proceed(request)
            }
        }

        return response
    }

    private fun parseRetryAfter(response: Response): Int? {
        val retryAfter = response.header("Retry-After")
        return retryAfter?.toInt()
    }
}