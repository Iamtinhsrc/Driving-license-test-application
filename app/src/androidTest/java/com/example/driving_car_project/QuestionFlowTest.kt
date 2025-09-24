package com.example.driving_car_project

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.driving_car_project.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class QuestionFlowTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun openQuestionDetail(){

        onView(withId(R.id.btn_questions)).perform(click())
        onView(withId(R.id.rv_questions)).check(matches(isDisplayed()))

        //Chon Item dau tien
        onView(withId(R.id.rv_questions))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, click()
                )
            )
        // Kiem tra Ui trong QuestionDetailFragment
        onView(withId(R.id.tv_question_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_answers)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_check_answer)).check(matches(isDisplayed()))
    }

}