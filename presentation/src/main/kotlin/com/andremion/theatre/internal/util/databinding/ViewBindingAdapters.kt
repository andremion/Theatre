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
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.ImageView
import com.andremion.theatre.internal.util.fade
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object ViewBindingAdapters {

    @JvmStatic
    @BindingAdapter("fadeView")
    fun fadeView(view: View, show: Boolean) {
        view.fade(show)
    }

    @JvmStatic
    @BindingAdapter("visible")
    fun setVisible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("showLongMessage")
    fun showLongMessage(view: View, text: String?) {
        text?.let {
            Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        }
    }

    @JvmStatic
    @BindingAdapter("loadUrl")
    fun loadUrl(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView.context)
                    .load(it)
                    .apply(RequestOptions.noTransformation())
                    .into(imageView)
        }
    }
}
