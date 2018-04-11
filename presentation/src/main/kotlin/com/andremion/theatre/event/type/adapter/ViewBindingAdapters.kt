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

package com.andremion.theatre.event.type.adapter

import android.databinding.BindingAdapter
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import com.andremion.domain.entity.EventType

object ViewBindingAdapters {

    @JvmStatic
    @BindingAdapter("eventTypeAdapter")
    fun setEventTypeAdapter(viewPager: ViewPager, items: List<EventType>?) {
        items?.let {
            val fm = (viewPager.context as FragmentActivity).supportFragmentManager
            viewPager.adapter = EventTypePagerAdapter(fm, it)
        }
    }
}
