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

package com.andremion.theatre.event.detail

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andremion.theatre.R
import com.andremion.theatre.databinding.FragmentEventDescriptionBinding
import com.andremion.theatre.internal.util.lazyThreadSafetyNone
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class EventDescriptionFragment : DaggerFragment() {

    companion object {

        fun newInstance(): EventDescriptionFragment {
            return EventDescriptionFragment()
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binder: FragmentEventDescriptionBinding

    private val viewModel by lazyThreadSafetyNone {
        // Using 'activity' as scope of EventDetailViewModel to reuse the same ViewModel instance
        activity?.let { ViewModelProviders.of(it, viewModelFactory).get(EventDetailViewModel::class.java) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_event_description, container, false)
        binder.viewModel = viewModel
        return binder.root
    }
}