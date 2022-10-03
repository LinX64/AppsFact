package com.example.appsfactory

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appsfactory.ui.view.MainActivity
import com.example.appsfactory.ui.view.search.SearchArtistFragment
import com.example.appsfactory.util.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class SearchArtistTest {

    lateinit var mainActivityScenario: ActivityScenario<MainActivity>
    lateinit var searchScenario: FragmentScenario<SearchArtistFragment>

    private val artistName = "The Beatles"

    @Test
    fun whenClickingSearchButtonWithoutParameter_shouldShowError() {
        mainActivityScenario = ActivityScenario.launch(MainActivity::class.java)

        launchSearchScreen()

        onView(withId(R.id.btnSend)).perform(click())
    }

    private fun launchSearchScreen() {
        launchFragmentInHiltContainer<SearchArtistFragment>()
    }
}