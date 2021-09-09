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

package com.andremion.theatre.navigation

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.andremion.theatre.event.detail.EventActivity
import com.andremion.theatre.home.HomeActivity

class Navigator {

    companion object {
        private val EXTRA_EVENT = "${EventActivity::class.java.`package`!!.name}.extra.EVENT"
    }

    fun navigateToHome(activity: Activity) {
        val intent = Intent(activity, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity.startActivity(intent)
    }

    fun navigateToEvent(activity: Activity, event: Int, sharedViews: Array<Pair<View, String>>) {
        val intent = Intent(activity, EventActivity::class.java)
        intent.putExtra(EXTRA_EVENT, event)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, *sharedViews).toBundle()
        ActivityCompat.startActivity(activity, intent, options)
    }

    fun getEvent(activity: Activity) = activity.intent.getIntExtra(EXTRA_EVENT, 0)

}
