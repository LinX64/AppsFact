package com.example.appsfactory

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
import com.example.appsfactory.data.source.local.entity.LocalAlbum
import com.example.appsfactory.presentation.MainActivity
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
        db = Room.databaseBuilder(
            getApplicationContext(),
            AppDatabase::class.java,
            "apps_factory_db"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

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
            LocalAlbum(
                count = 0,
                name = "Album $it",
                artist = "Artist $it",
                image = "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png"
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

    @After
    fun tearDown() {
        closeMainScreen()
    }
}