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

package com.andremion.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Event")
data class EventLocalModel(
        @PrimaryKey var id: Int,
        var type: Int,
        var name: String,
        var description: String? = null,
        var venue: Int,
        val runningTime: String? = null,
        var tagLine: String? = null,
        var image: String? = null,
        var thumbnail: String? = null,
        var url: String? = null,
        val currentPrice: Float,
        val offerPrice: Float,
        val minimumPrice: Float
)
