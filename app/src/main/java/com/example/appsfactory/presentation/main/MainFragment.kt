/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:44 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:44 PM
 *
 */

package com.example.appsfactory.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appsfactory.data.source.local.entity.AlbumEntity
import com.example.appsfactory.databinding.FragmentMainBinding
import com.example.appsfactory.presentation.base.BaseFragment
import com.example.appsfactory.presentation.util.gone
import com.example.appsfactory.presentation.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var topAlbumsAdapter: TopAlbumsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        getAlbums()
    }

    private fun setupUI() {
        topAlbumsAdapter = TopAlbumsAdapter(this::onAlbumClicked)
        binding.recyclerViewMain.adapter = topAlbumsAdapter
    }

    private fun onAlbumClicked(album: AlbumEntity) {
        val id = album.id
        val name = album.name
        val artistName = album.artist

        val action =
            MainFragmentDirections.actionMainFragmentToDetailFragment(id, name, artistName)
        findNavController().navigate(action)
    }

    private fun getAlbums() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel
                .mAlbums
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { result -> submitList(result) }
        }
    }

    private fun submitList(localAlbums: List<AlbumEntity>) {
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