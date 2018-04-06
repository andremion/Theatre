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

package com.andremion.theatre.internal.util

import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter
import android.view.View

/**
 * @param show true, if the view will fade-in or false to fade-out
 */
fun View.fade(show: Boolean) {

    // Cancel any on-going animation
    ViewCompat.animate(this).cancel()

    if (show) {
        if (!isShown) {
            visibility = View.VISIBLE
            alpha = 0f
            ViewCompat.animate(this).alpha(1f).start()
        }
    } else {
        if (isShown) {
            alpha = 1f
            ViewCompat.animate(this)
                    .alpha(0f)
                    .setListener(object : ViewPropertyAnimatorListenerAdapter() {
                        override fun onAnimationEnd(view: View?) {
                            ViewCompat.animate(view).setListener(null)
                            view?.visibility = View.INVISIBLE
                        }
                    })
                    .start()
        }
    }
}
