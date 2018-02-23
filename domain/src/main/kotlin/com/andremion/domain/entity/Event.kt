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

package com.andremion.domain.entity

data class Event(
        val id: Int,
        val type: Int,
        val name: String,
        val description: String?,
        val venue: Int,
        val runningTime: String?,
        val tagLine: String?,
        val image: String?,
        val thumbnail: String?,
        val url: String?,
        val currentPrice: Float,
        val offerPrice: Float,
        val minimumPrice: Float)