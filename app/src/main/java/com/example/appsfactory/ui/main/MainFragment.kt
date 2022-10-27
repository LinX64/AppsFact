/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:44 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:44 PM
 *
 */

package com.example.appsfactory.ui.main

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.appsfactory.data.source.local.entity.TopAlbumEntity
import com.example.appsfactory.databinding.FragmentMainBinding
import com.example.appsfactory.ui.base.BaseFragment
import com.example.appsfactory.ui.util.gone
import com.example.appsfactory.ui.util.observeWithLifecycle
import com.example.appsfactory.ui.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var topAlbumsAdapter: TopAlbumAdapter

    override fun setupUI() {
        super.setupUI()

        setupRecyclerView()
        getAlbums()
    }

    private fun setupRecyclerView() {
        topAlbumsAdapter = TopAlbumAdapter(this::onAlbumClicked)
        with(binding.recyclerViewMain) {
            adapter = topAlbumsAdapter
            setHasFixedSize(true)
        }
    }

    private fun onAlbumClicked(album: TopAlbumEntity) {
        val id = album.id
        val name = album.name
        val artistName = album.artist

        val action =
            MainFragmentDirections.actionMainFragmentToDetailFragment(id, name, artistName)
        findNavController().navigate(action)
    }

    private fun getAlbums() {
        mainViewModel
            .mAlbums
            .observeWithLifecycle(this) { submitList(it) }
    }

    private fun submitList(localAlbums: List<TopAlbumEntity>) {
        if (localAlbums.isEmpty()) {
            binding.recyclerViewMain.gone()
            binding.emptyView.emptyViewLayout.visible()
        } else {
            binding.recyclerViewMain.visible()
            binding.emptyView.emptyViewLayout.gone()
            topAlbumsAdapter.submitList(localAlbums)
        }
    }
}