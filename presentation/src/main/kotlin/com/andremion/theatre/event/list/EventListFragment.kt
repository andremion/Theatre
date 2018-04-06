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

package com.andremion.theatre.event.list

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.util.Pair
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andremion.theatre.R
import com.andremion.theatre.databinding.FragmentEventListBinding
import com.andremion.theatre.event.list.adapter.EventListAdapter
import com.andremion.theatre.event.list.model.EventModel
import com.andremion.theatre.internal.util.lazyThreadSafetyNone
import com.andremion.theatre.navigation.Navigator
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class EventListFragment : DaggerFragment(), EventListAdapter.Callbacks {

    companion object {

        private const val ARG_TYPE = "type"

        fun newInstance(type: Int): EventListFragment {
            val fragment = EventListFragment()
            val args = Bundle()
            args.putSerializable(ARG_TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var navigator: Navigator

    private lateinit var binder: FragmentEventListBinding

    private val viewModel by lazyThreadSafetyNone {
        ViewModelProviders.of(this, viewModelFactory).get(EventListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_event_list, container, false)
        binder.viewModel = viewModel
        binder.eventCallbacks = this
        return binder.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val eventType = arguments?.getInt(ARG_TYPE)!!
        viewModel.loadEventList(eventType)
    }

    override fun onItemClick(view: View, item: EventModel) {
        val cardView = view.findViewById<View>(R.id.cardview)
        val imageView = view.findViewById<View>(R.id.image_thumbnail)
        val nameView = view.findViewById<View>(R.id.text_name)
        val sharedViews = arrayOf(
                Pair(cardView, ViewCompat.getTransitionName(cardView)),
                Pair(imageView, ViewCompat.getTransitionName(imageView)),
                Pair(nameView, ViewCompat.getTransitionName(nameView)))
        activity?.let { navigator.navigateToEvent(it, item.id, sharedViews) }
    }
}
