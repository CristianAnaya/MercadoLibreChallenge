package com.cranaya.mercadolibrechallenge

import androidx.annotation.CallSuper
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.cranaya.mercadolibrechallenge.core.base.BaseViewHolderAdapter
import com.cranaya.mercadolibrechallenge.presentation.features.homeActivity.HomeActivity
import com.cranaya.mercadolibrechallenge.utils.CustomMatchers.softCheckMatcher
import com.cranaya.mercadolibrechallenge.utils.Wait
import org.junit.Before
import org.junit.Rule

open class BaseEspressoTest {

    @get:Rule
    internal var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    @CallSuper
    open fun setUp() {
        Wait.waitForCondition(message = "Main Activity didn't load in time") {
            when {
                softCheckMatcher(withId(R.id.search_view_container), ViewMatchers.isCompletelyDisplayed())
                -> {
                    return@waitForCondition true
                }
                else -> {
                }
            }
        }
    }
}
