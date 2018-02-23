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

package com.andremion.theatre.event.detail.rating

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.andremion.theatre.R
import com.andremion.theatre.databinding.FragmentReviewListBinding
import com.andremion.theatre.internal.injection.ViewModelFactory
import com.andremion.theatre.internal.util.lazyThreadSafetyNone
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ReviewListFragment : Fragment() {

    companion object {

        private const val ARG_EVENT = "event"

        fun newInstance(event: Int): ReviewListFragment {
            val fragment = ReviewListFragment()
            val args = Bundle()
            args.putInt(ARG_EVENT, event)
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binder: FragmentReviewListBinding

    private val viewModel by lazyThreadSafetyNone {
        ViewModelProviders.of(this, viewModelFactory).get(EventRatingViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_review_list, container, false)
        binder.viewModel = viewModel
        return binder.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val event = arguments?.getInt(ARG_EVENT)!!
        viewModel.loadEventRating(event)
    }
}