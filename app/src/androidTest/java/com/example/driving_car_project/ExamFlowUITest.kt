package com.example.driving_car_project

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.driving_car_project.exam.ExamDetailAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.driving_car_project.feature.exam.R

@HiltAndroidTest
class ExamFlowUiTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun examFlow_submitAndViewResult_displaysCorrectly() {

        // Navigate to ExamFragment
        onView(withId(com.example.driving_car_project.feature.home.R.id.btn_exam)).perform(click())

        // Navigate to ExamDetailFragment
        onView(withId(R.id.btn_exam_10)).perform(click())
        Thread.sleep(5000)

        // Check RecyclerView exists
        onView(withId(R.id.rv_exam_answers)).check(matches(isDisplayed()))

        // Click first option
        onView(withId(R.id.rv_exam_answers))
            .perform(RecyclerViewActions.actionOnItemAtPosition<ExamDetailAdapter.AnswerOptionViewHolder>(0, click()))

        // Submit exam
        onView(withId(R.id.btn_submit_exam)).perform(click())
        onView(withText(R.string.dialog_submit)).perform(click())

        // Result Fragment check
        onView(withId(R.id.tv_result_summary)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_result_status)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_result_time)).check(matches(isDisplayed()))

        // Click review
        onView(withId(R.id.btn_review_exam)).perform(click())
        onView(withId(R.id.rv_exam_answers)).check(matches(isDisplayed()))
    }
}
