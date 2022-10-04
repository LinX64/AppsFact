package com.example.appsfactory

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
import com.example.appsfactory.data.source.local.dao.AlbumsDao
import com.example.appsfactory.data.source.local.entity.LocalAlbum
import com.example.appsfactory.presentation.MainActivity
import com.example.appsfactory.util.assertions.RecyclerViewItemCountAssertion
import com.example.appsfactory.util.clickOnFirstItem
import com.example.appsfactory.util.waitAndRetry
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class DetailScreenTest {

    private lateinit var mainActivityScenario: ActivityScenario<MainActivity>
    private val searchQuery = "Eminem"

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var albumsDao: AlbumsDao

    @Before
    fun setup() {
        db = Room.databaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java,
            "apps_factory_db"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        clearTables()

        albumsDao = db.albumDao()
    }

    @Test
    fun whenThereIsAnAlbumAvailableOnMain_Then_ShouldGoToDetailScreen(): Unit = runBlocking {
        insertDummyData()

        launchMainActivity()

        waitAndRetry { onView(withId(R.id.recyclerViewMain)).perform(clickOnFirstItem()) }

        onView(withId(R.id.artistName)).check(matches(isDisplayed()))
    }

    @Test
    fun whenAnAlbumFound_Then_ShouldGoToTopAlbumAndAlbumDetail(): Unit = runBlocking {
        insertDummyData()

        launchMainActivityAndSearch()

        waitAndRetry { onView(withId(R.id.recyclerView)).perform(clickOnFirstItem()) }

        waitAndRetry {
            onView(withId(R.id.recyclerViewTopAlbums)).check(
                RecyclerViewItemCountAssertion { it > 0 })

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

    private suspend fun insertDummyData() {
        val album = LocalAlbum(
            name = "album",
            artist = "artist",
            image = "image",
            url = "url",
            isSelected = true
        )
        albumsDao.insertAlbum(album)
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