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

package com.andremion.data.repository.mapper

import com.andremion.data.local.model.VenueLocalModel
import com.andremion.data.remote.model.VenueRemoteModel

class VenueMapper {

    fun toLocal(venue: VenueRemoteModel) = VenueLocalModel(venue.id, venue.name ?: "")
}