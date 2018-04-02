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
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.os.Bundle
import android.view.Menu
import android.view.ViewGroup
import androidx.view.doOnPreDraw
import com.andremion.theatre.R
import com.andremion.theatre.databinding.ActivityEventBinding
import com.andremion.theatre.event.detail.rating.EventRatingViewModel
import com.andremion.theatre.internal.util.lazyThreadSafetyNone
import com.andremion.theatre.navigation.Navigator
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class EventActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var navigator: Navigator

    private val binder by lazyThreadSafetyNone<ActivityEventBinding> {
        DataBindingUtil.setContentView(this, R.layout.activity_event)
    }

    private val eventDetailViewModel by lazyThreadSafetyNone {
        ViewModelProviders.of(this, viewModelFactory).get(EventDetailViewModel::class.java)
    }

    private val eventRatingViewModel by lazyThreadSafetyNone {
        ViewModelProviders.of(this, viewModelFactory).get(EventRatingViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportPostponeEnterTransition()

        setSupportActionBar(binder.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binder.eventDetailViewModel = eventDetailViewModel
        binder.eventRatingViewModel = eventRatingViewModel

        val event = navigator.getEvent(this)
        eventDetailViewModel.loadEventDetail(event)
        eventRatingViewModel.loadEventRating(event)

        eventDetailViewModel.event.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable, propertyId: Int) {
                (window.decorView as ViewGroup).doOnPreDraw {
                    supportStartPostponedEnterTransition()
                }
            }
        })
    }

    override fun getParentActivityIntent(): Intent {
        return super.getParentActivityIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_event, menu)
        return true
    }
}
