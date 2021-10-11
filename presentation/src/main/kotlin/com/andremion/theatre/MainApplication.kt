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

package com.andremion.theatre

import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.andremion.theatre.internal.injection.DaggerApplication
import com.facebook.stetho.Stetho
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import java.io.IOException
import java.net.SocketException


class MainApplication : DaggerApplication() {

    companion object {
        private const val LOG_TAG = "Theater"
    }

    /**
     * Versions of the platform prior to Android 5.0 (API level 21) use the Dalvik runtime for executing app code.
     * By default, Dalvik limits apps to a single classes.dex bytecode file per APK.
     * In order to get around this limitation, we can add the multidex support library to project.
     */
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        setupReactiveX()
    }

    private fun setupReactiveX() {
        // Error handling in RxJava
        // https://github.com/ReactiveX/RxJava/wiki/What%27s-different-in-2.0#error-handling
        RxJavaPlugins.setErrorHandler { e ->
            if (e is UndeliverableException) {
                Log.w(LOG_TAG, "Undeliverable exception received, not sure what to do", e.cause)
                return@setErrorHandler
            }
            if (e is IOException || e is SocketException) {
                // fine, irrelevant network problem or API that throws on cancellation
                return@setErrorHandler
            }
            if (e is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return@setErrorHandler
            }
            if (e is NullPointerException || e is IllegalArgumentException) {
                // that's likely a bug in the application
                Thread.currentThread().uncaughtExceptionHandler
                    ?.uncaughtException(Thread.currentThread(), e)
                return@setErrorHandler
            }
            if (e is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().uncaughtExceptionHandler
                    ?.uncaughtException(Thread.currentThread(), e)
                return@setErrorHandler
            }
        }
    }
}
