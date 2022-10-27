package com.example.appsfactory

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appsfactory.data.source.local.AppDatabase
import com.example.appsfactory.data.source.local.dao.TopAlbumsDao
import com.example.appsfactory.data.source.local.entity.TopAlbumEntity
import com.example.appsfactory.ui.MainActivity
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class MainScreenTest {

    private lateinit var mainActivityScenario: ActivityScenario<MainActivity>

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var albumsDao: TopAlbumsDao

    @Before
    fun setup() {
        setupDb()

        albumsDao = db.topAlbumDao()
    }

    @Test
    fun whenAlbumsListIsNotEmptyThenRecyclerViewShouldBeShown(): Unit = runBlocking {
        insertDummyData()

        launchMainScreen()

        onView(withId(R.id.recyclerViewMain)).check(matches((isDisplayed())))

        clearTables()
    }

    @Test
    fun whenAlbumsListIsEmptyThenRecyclerViewShouldNotBeShown(): Unit = runBlocking {
        clearTables()

        launchMainScreen()

        onView(withId(R.id.recyclerViewMain)).check(matches(not(isDisplayed())))
    }

    private suspend fun insertDummyData() {
        val albums = List(10) {
            TopAlbumEntity(
                0,
                name = "Album $it",
                artist = "Artist $it",
                image = "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2be3b5b7e943.png",
                isBookmarked = 1
            )
        }
        albumsDao.insertAll(albums)
    }

    private fun clearTables() {
        db.clearAllTables()
    }

    private fun launchMainScreen() {
        mainActivityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    private fun closeMainScreen() {
        mainActivityScenario.close()
    }

    private fun setupDb() {
        val context = getApplicationContext<Context>()
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "apps_factory_db"
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun tearDown() {
        closeMainScreen()
    }
}