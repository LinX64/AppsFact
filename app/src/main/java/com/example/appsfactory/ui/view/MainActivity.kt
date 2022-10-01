package com.example.appsfactory.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appsfactory.R
import com.example.appsfactory.ui.view.main.MainFragment
import com.example.appsfactory.ui.view.search.SearchArtistFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
    }

    private fun setupUI() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container_view, SearchArtistFragment())
            .commitNow()
    }
}