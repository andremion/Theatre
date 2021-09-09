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

package com.andremion.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andremion.data.local.model.EventTypeLocalModel
import io.reactivex.Maybe

@Dao
interface EventTypeDao {

    @Query("SELECT * FROM EventType WHERE id = :id")
    fun getById(id: Int): Maybe<EventTypeLocalModel>

    @Query("SELECT * FROM EventType")
    fun getAll(): Maybe<List<EventTypeLocalModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg eventTypes: EventTypeLocalModel)
}
