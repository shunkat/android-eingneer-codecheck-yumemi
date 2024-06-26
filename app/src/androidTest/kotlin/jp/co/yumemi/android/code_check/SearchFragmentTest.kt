package jp.co.yumemi.android.code_check


import androidx.test.espresso.DataInteraction
import androidx.test.espresso.ViewInteraction
import androidx.test.filters.LargeTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

import androidx.test.InstrumentationRegistry.getInstrumentation
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*

import jp.co.yumemi.android.code_check.R

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.`is`

@LargeTest
@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    // searchFragmentが正しく表示されるかどうかのテスト
    // recyclerViewの内部のテストは実装工数的に今は実装しない
    @Test
    fun searchFragmentTest() {
        val textInputEditText = onView(
            allOf(withId(R.id.searchInputText), isDisplayed())
        )
        textInputEditText.perform(replaceText("test"), closeSoftKeyboard())

        val recyclerView = onView(
            allOf(withId(R.id.rvRepositoryList), isDisplayed())
        )
        recyclerView.check(matches(isDisplayed()))
        textInputEditText.perform(pressImeActionButton())
    }
}
