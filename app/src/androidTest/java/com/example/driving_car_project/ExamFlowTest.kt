//package com.example.driving_car_project
//
//import androidx.recyclerview.widget.RecyclerView
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.IdlingRegistry
//import androidx.test.espresso.action.ViewActions.click
//import androidx.test.espresso.action.ViewActions.scrollTo
//import androidx.test.espresso.assertion.ViewAssertions.matches
//import androidx.test.espresso.contrib.RecyclerViewActions
//import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
//import androidx.test.espresso.matcher.ViewMatchers.withId
//import androidx.test.espresso.matcher.ViewMatchers.withText
//import androidx.test.ext.junit.rules.ActivityScenarioRule
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.driving_car_project.ui.MainActivity
//import dagger.hilt.android.testing.HiltAndroidRule
//import dagger.hilt.android.testing.HiltAndroidTest
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//
//@HiltAndroidTest
//@RunWith(AndroidJUnit4::class)
//class ExamFlowTest {
//
//    @get:Rule(order = 0)
//    var hiltRule = HiltAndroidRule(this)
//
//    @get:Rule(order = 1)
//    var activityRule = ActivityScenarioRule(MainActivity::class.java)
//
//    @Before
//    fun setup(){
//        hiltRule.inject()
//    }
//
//    @Test
//    fun completeExamFlow() {
//
//        onView(withId(R.id.btn_exam)).perform(click())
//        onView(withId(R.id.btn_exam_10)).check(matches(isDisplayed()))
//
//        //1.Click vao Button De 10 cau
//        onView(withId(R.id.btn_exam_10)).perform(click())
//
//        // Đăng ký IdlingResource để chờ dữ liệu load vào RecyclerView
//        var idlingResource: RecyclerViewItemCountIdlingResource? = null
//
//        activityRule.scenario.onActivity { activity ->
//            val recyclerView = activity.findViewById<RecyclerView>(R.id.rv_exam_answers)
//            idlingResource = RecyclerViewItemCountIdlingResource(recyclerView, 1)
//            IdlingRegistry.getInstance().register(idlingResource)
//        }
//
//        //2. Kiem tra UI
//        onView(withId(R.id.tv_exam_question)).check(matches(isDisplayed()))
//        onView(withId(R.id.rv_exam_answers)).check(matches(isDisplayed()))
//
//
//
//        //3. Chon phan tu dau tien trong RecyclerView
//        onView(withId(R.id.rv_exam_answers))
//            .check { view, _ ->
//                val rv = view as RecyclerView
//                assert(rv.adapter!!.itemCount > 0) { "RecyclerView chưa có dữ liệu" }
//            }
//
//        onView(withId(R.id.rv_exam_answers))
//            .perform(
//                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
//                    0, click()
//                )
//            )
//
//
//        //4. Submit exam
//        onView(withId(R.id.btn_submit_exam)).perform(click())
//
//        // Xác nhận dialog hiện
//        onView(withText("Bạn có chắc muốn nộp bài không"))
//            .check(matches(isDisplayed()))
//
//       // Dialog
//        onView(withText("Nộp")).perform(click())
//
//        //5
//        onView(withId(R.id.tv_result_summary)).check(matches(isDisplayed()))
//        onView(withId(R.id.tv_result_status)).check(matches(isDisplayed()))
//        onView(withId(R.id.tv_result_time)).check(matches(isDisplayed()))
//
//        IdlingRegistry.getInstance().unregister(idlingResource)
//
//    }
//}