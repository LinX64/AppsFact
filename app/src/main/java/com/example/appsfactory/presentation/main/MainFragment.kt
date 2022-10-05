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
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appsfactory.data.source.local.entity.LocalAlbum
import com.example.appsfactory.databinding.FragmentMainBinding
import com.example.appsfactory.presentation.base.BaseFragment
import com.example.appsfactory.presentation.util.gone
import com.example.appsfactory.presentation.util.visible
import com.example.appsfactory.util.UiState
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

    private fun onAlbumClicked(album: LocalAlbum) {
        val name = album.name
        val artistName = album.artist

        val action =
            MainFragmentDirections.actionMainFragmentToDetailFragment(name, artistName)
        findNavController().navigate(action)
    }

    private fun getAlbums() {
        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.uiState.flowWithLifecycle(
                viewLifecycleOwner.lifecycle,
                Lifecycle.State.STARTED
            )
                .collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> binding.progressBar.visible()
                        is UiState.Success -> onSuccess(uiState)
                        is UiState.Error -> onError(uiState)
                    }
                }
        }
    }

    private fun onSuccess(uiState: UiState.Success<List<LocalAlbum>>) {
        binding.progressBar.gone()
        submitList(uiState.data)
    }

    private fun onError(result: UiState.Error) {
        binding.progressBar.gone()
        Toast.makeText(
            requireContext(),
            result.error,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun submitList(localAlbums: List<LocalAlbum>) {
        if (localAlbums.isEmpty()) {
            binding.recyclerViewMain.gone()
            binding.emptyView.emptyViewLayout.visible()
        } else {
            binding.recyclerViewMain.visible()
            binding.emptyView.emptyViewLayout.gone()
            topAlbumsAdapter.setData(localAlbums)
        }
    }
}