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

package com.andremion.theatre.home

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.andremion.theatre.R
import com.andremion.theatre.util.BaseActivityTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HomeActivityTest : BaseActivityTest() {

    @Rule
    @JvmField
    val activityRule: ActivityTestRule<*> = ActivityTestRule<HomeActivity>(HomeActivity::class.java)

    @Before
    fun setUp() {
        unlockScreen(activityRule)
    }

    /**
     * Temporary test. Just check if app doesn't crash on opening.
     */
    @Test
    @Throws(Exception::class)
    fun givenFabShownWhenClickOnItShouldShowASnackbar() {
        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.snackbar_text)).check(matches(withText("Replace with your own action")))
    }
}