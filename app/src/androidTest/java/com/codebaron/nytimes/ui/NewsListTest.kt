package com.codebaron.nytimes.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.codebaron.nytimes.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsListTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(NewsListActivity::class.java)

    @Test
    fun title_should_be_trending() {
        Espresso.onView(ViewMatchers.withId(R.id.toolbar_title))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.toolbar_title))
            .check(ViewAssertions.matches(ViewMatchers.withText("NY Times")))
    }
}