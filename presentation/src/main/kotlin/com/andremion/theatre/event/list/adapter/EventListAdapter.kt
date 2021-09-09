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

package com.andremion.theatre.event.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.andremion.theatre.R
import com.andremion.theatre.databinding.FragmentEventListItemBinding
import com.andremion.theatre.event.list.model.EventModel

class EventListAdapter(private val items: List<EventModel>, private val callbacks: Callbacks? = null) :
        RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    interface Callbacks {
        fun onItemClick(view: View, item: EventModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: FragmentEventListItemBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.event = items[position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: FragmentEventListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener { callbacks?.onItemClick(it, items[bindingAdapterPosition]) }
        }
    }
}
