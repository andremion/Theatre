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

package com.andremion.data.local.inventory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andremion.data.local.dao.EventDao
import com.andremion.data.local.dao.RatingDao
import com.andremion.data.local.model.EventLocalModel
import com.andremion.data.local.model.RatingLocalModel
import com.andremion.data.local.model.ReviewLocalModel
import com.andremion.data.local.util.Converters

@Database(entities = [EventLocalModel::class, RatingLocalModel::class, ReviewLocalModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class InventoryDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao

    abstract fun ratingDao(): RatingDao

    companion object {
        fun newInstance(context: Context): InventoryDatabase {
            return Room.inMemoryDatabaseBuilder(context, InventoryDatabase::class.java).build()
        }
    }
}
