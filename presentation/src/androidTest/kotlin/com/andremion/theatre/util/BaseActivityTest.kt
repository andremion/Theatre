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

package com.andremion.theatre.util

import android.os.Build
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.WindowManager
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseActivityTest {

    protected fun unlockScreen(activityRule: ActivityTestRule<*>) {
        val activity = activityRule.activity
        val wakeUpDevice = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            activity.setTurnScreenOn(true)
            activity.setShowWhenLocked(true)
            Runnable {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        } else {
            Runnable {
                @Suppress("DEPRECATION")
                activity.window.addFlags(
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                )
            }
        }
        activity.runOnUiThread(wakeUpDevice)
    }
}