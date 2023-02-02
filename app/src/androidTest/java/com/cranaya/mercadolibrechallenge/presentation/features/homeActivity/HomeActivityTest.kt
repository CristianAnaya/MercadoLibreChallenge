package com.cranaya.mercadolibrechallenge.presentation.features.homeActivity

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.cranaya.mercadolibrechallenge.BaseEspressoTest
import com.cranaya.mercadolibrechallenge.utils.CustomMatchers.waitViewIsDisplayed
import org.junit.Test
import org.junit.runner.RunWith
import com.cranaya.mercadolibrechallenge.R
import com.cranaya.mercadolibrechallenge.utils.RecyclerViewMatcher
import org.junit.Rule


@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeActivityTest : BaseEspressoTest(){

    @get:Rule
    var mActivityTestRule: ActivityScenarioRule<HomeActivity> = ActivityScenarioRule(
        HomeActivity::class.java
    )

    @Test
    fun checkSearchLabelChanges() {

        //Go to search view
        onView(withId(R.id.edit_text_search)).perform(click())

        //Do search
        val query = "Samsung"
        onView(withId(R.id.edit_text_query))
            .perform(replaceText(query), pressImeActionButton())

        //Go back to home page
        waitViewIsDisplayed(withId(R.id.loading_progress_bar))

        // Check that the search label was changed.
        onView(withId(R.id.edit_text_search)).check(matches(withText(query)))
    }

    @Test
    fun checkEmptyView() {

        //Go to search view
        onView(withId(R.id.edit_text_search)).perform(click())

        //Do search
        val query = "aslkfja slkjfaskdl jfaklsjf laks"
        onView(withId(R.id.edit_text_query)).perform(replaceText(query), pressImeActionButton())

        // Check that the search label was changed.
        val labelEmpty = ""
        onView(withId(R.id.edit_text_search)).check(matches(withText(labelEmpty)))
    }

    @Test
    fun checkGoToDetailsView() {

        //Go to search view
        onView(withId(R.id.edit_text_search)).perform(click())

        //Do search
        val query = "Samsung"
        onView(withId(R.id.edit_text_query))
            .perform(replaceText(query), pressImeActionButton())

        //Go to details
        waitViewIsDisplayed(withId(R.id.product_image))

        onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))


        //Check that details page is showed
        waitViewIsDisplayed(withId(R.id.product_detail_attributes))

    }

}