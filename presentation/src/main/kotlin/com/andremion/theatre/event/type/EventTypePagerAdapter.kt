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

package com.andremion.theatre.event.type

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.andremion.domain.entity.EventType
import com.andremion.theatre.event.list.EventListFragment

class EventTypePagerAdapter(fm: FragmentManager, private val items: List<EventType>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return EventListFragment.newInstance(items[position].id)
    }

    override fun getCount() = items.size

    override fun getPageTitle(position: Int) = items[position].name
}
