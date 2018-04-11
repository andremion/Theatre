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

package com.andremion.data.local.system

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.andremion.data.local.dao.EventTypeDao
import com.andremion.data.local.dao.VenueDao
import com.andremion.data.local.model.EventTypeLocalModel
import com.andremion.data.local.model.VenueLocalModel
import com.andremion.data.local.util.Converters

@Database(entities = [EventTypeLocalModel::class, VenueLocalModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class SystemDatabase : RoomDatabase() {

    abstract fun eventTypeDao(): EventTypeDao

    abstract fun venueDao(): VenueDao

    companion object {
        fun newInstance(context: Context): SystemDatabase {
            return Room.databaseBuilder(context, SystemDatabase::class.java, "theatre-system.db").build()
        }
    }
}