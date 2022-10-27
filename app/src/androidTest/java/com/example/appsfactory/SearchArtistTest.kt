package com.example.appsfactory

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appsfactory.ui.MainActivity
import com.example.appsfactory.util.ToastMatcher
import com.example.appsfactory.util.assertions.RecyclerViewItemCountAssertion
import com.example.appsfactory.util.clickOnFirstItem
import com.example.appsfactory.util.waitAndRetry
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SearchArtistTest {

    private lateinit var mainActivityScenario: ActivityScenario<MainActivity>
    private val artistName = "The Beatles"

    @Test
    fun whenClickingSearchButtonWithoutParameter_shouldShowError() {
        launchSearchScreen()

        Thread.sleep(1000)

        onView(withId(R.id.btnSend)).perform(click())

        Thread.sleep(1000)

        val expectedWarning = "Please enter artist name"
        onView(withText(expectedWarning))
            .inRoot(ToastMatcher().apply {
                matches(isDisplayed())
            })
    }

    @Test
    fun whenTypingATextOnEditTextShouldShowAtLeastAnItem() {
        launchSearchScreen()

        onView(withId(R.id.editText)).perform(typeText(artistName))

        onView(withId(R.id.btnSend)).perform(click())

        waitAndRetry {
            onView(withId(R.id.recyclerView)).check(
                RecyclerViewItemCountAssertion { it > 0 })
        }
    }

    @Test
    fun whenListIsNotEmpty_THEN_ShouldGoToTopAlbums() {
        launchSearchScreen()

        onView(withId(R.id.editText)).perform(typeText(artistName))

        onView(withId(R.id.btnSend)).perform(click())

        waitAndRetry {
            onView(withId(R.id.recyclerView)).perform(clickOnFirstItem())
        }
    }

    private fun launchSearchScreen() {
        mainActivityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.search_action)).perform(click())
    }

    private fun closeScenario() {
        mainActivityScenario.close()
    }

    @After
    fun tearDown() {
        closeScenario()
    }
}