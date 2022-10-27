package com.example.appsfactory

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appsfactory.ui.MainActivity
import com.example.appsfactory.util.assertions.RecyclerViewItemCountAssertion
import com.example.appsfactory.util.clickOnFirstItem
import com.example.appsfactory.util.waitAndRetry
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TopAlbumsTest {

    private lateinit var mainActivityScenario: ActivityScenario<MainActivity>
    private val searchQuery = "Eminem"

    @Test
    fun whenAnAlbumFound_Then_ShouldGoToTopAlbums() {
        launchMainActivityAndSearch()

        waitAndRetry { onView(withId(R.id.recyclerView)).perform(clickOnFirstItem()) }

        waitAndRetry {
            onView(withId(R.id.recyclerViewTopAlbums)).check(
                RecyclerViewItemCountAssertion { it > 0 })
        }
    }

    @Test
    fun whenAnAlbumFound_Then_ShouldGoToTopAlbumAndAlbumDetail() {
        launchMainActivityAndSearch()

        waitAndRetry { onView(withId(R.id.recyclerView)).perform(clickOnFirstItem()) }

        waitAndRetry {
            onView(withId(R.id.recyclerViewTopAlbums)).check(
                RecyclerViewItemCountAssertion { it > 0 })
        }

        waitAndRetry {
            onView(withId(R.id.recyclerViewTopAlbums)).perform(clickOnFirstItem())
        }

        onView(withId(R.id.titleTV)).check(matches(isDisplayed()))
    }

    private fun launchMainActivity() {
        mainActivityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    private fun closeMainActivity() {
        mainActivityScenario.close()
    }

    private fun launchMainActivityAndSearch() {
        launchMainActivity()

        onView(withId(R.id.search_action)).perform(click())

        onView(withId(R.id.editText)).perform(typeText(searchQuery))
        onView(withId(R.id.btnSend)).perform(click())
    }

    @After
    fun tearDown() {
        closeMainActivity()
    }
}