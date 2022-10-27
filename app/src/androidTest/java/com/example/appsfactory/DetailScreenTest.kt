package com.example.appsfactory

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appsfactory.data.source.local.AppDatabase
import com.example.appsfactory.data.source.local.dao.TopAlbumsDao
import com.example.appsfactory.ui.MainActivity
import com.example.appsfactory.util.assertions.RecyclerViewItemCountAssertion
import com.example.appsfactory.util.clickOnFirstItem
import com.example.appsfactory.util.waitAndRetry
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class DetailScreenTest {

    private lateinit var mainActivityScenario: ActivityScenario<MainActivity>
    private val searchQuery = "Justin Bieber"

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var albumsDao: TopAlbumsDao

    @Before
    fun setup() {
        setupDb()
        clearTables()
        albumsDao = db.topAlbumDao()
    }

    @Test
    fun whenAnAlbumFound_Then_ShouldGoToTopAlbumAndAlbumDetail(): Unit = runBlocking {
        launchMainActivityAndSearch()

        waitAndRetry { onView(withId(R.id.recyclerView)).perform(clickOnFirstItem()) }

        waitAndRetry {
            onView(withId(R.id.recyclerViewTopAlbums)).check(RecyclerViewItemCountAssertion { it > 0 })
        }

        waitAndRetry { onView(withId(R.id.recyclerViewTopAlbums)).perform(clickOnFirstItem()) }

        delay(1000)

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

    private fun setupDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "apps_factory_db"
        )
            .allowMainThreadQueries()
            .build()
    }

    private fun clearTables() {
        db.clearAllTables()
    }

    @After
    fun tearDown() {
        clearTables()
        closeMainActivity()
    }
}