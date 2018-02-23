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

package com.andremion.theatre.internal.util.databinding

import android.databinding.BindingAdapter
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import com.andremion.domain.entity.Review
import com.andremion.theatre.event.detail.rating.ReviewListAdapter
import com.andremion.theatre.event.list.EventListAdapter
import com.andremion.theatre.event.list.model.EventModel

object RecyclerViewBindingAdapters {

    @JvmStatic
    @BindingAdapter("eventAdapter", "eventCallbacks", requireAll = false)
    fun setEventAdapter(recyclerView: RecyclerView, items: List<EventModel>?, callbacks: EventListAdapter.Callbacks?) {
        items?.let {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = EventListAdapter(it, callbacks)
        }
    }

    @JvmStatic
    @BindingAdapter("reviewAdapter")
    fun setReviewAdapter(recyclerView: RecyclerView, items: List<Review>?) {
        items?.let {
            if (recyclerView.itemDecorationCount == 0) {
                recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
            }
            recyclerView.adapter = ReviewListAdapter(it)
        }
    }
}
