/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:48 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:47 PM
 *
 */

package com.example.appsfactory.presentation.top_albums

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appsfactory.databinding.FragmentTopAlbumsBinding
import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.presentation.base.BaseFragment
import com.example.appsfactory.presentation.util.inVisible
import com.example.appsfactory.presentation.util.visible
import com.example.appsfactory.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopAlbumsFragment :
    BaseFragment<FragmentTopAlbumsBinding>(FragmentTopAlbumsBinding::inflate) {

    private val topAlbumsViewModel by activityViewModels<TopAlbumsViewModel>()
    private lateinit var topAlbumsAdapter: TopAlbumAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        setupRecyclerView()

        getArtistNameAndObserve()
    }

    private fun setupRecyclerView() {
        topAlbumsAdapter = TopAlbumAdapter(
            this::onAlbumClicked,
            this::onBookmarkClicked,
            this::onBookmarkRemoveClicked
        )
        binding.recyclerViewTopAlbums.adapter = topAlbumsAdapter
    }

    private fun onBookmarkClicked(album: TopAlbum) {
        topAlbumsViewModel.onBookmarkClicked(album)
        Toast.makeText(requireContext(), "Bookmarked!", Toast.LENGTH_SHORT).show()
    }

    private fun onBookmarkRemoveClicked(album: TopAlbum) {
        topAlbumsViewModel.onBookmarkRemoveClicked(album)
    }

    private fun onAlbumClicked(album: TopAlbum) {
        val id = album.playcount
        val name = album.name
        val artist = album.artist.name

        val action = TopAlbumsFragmentDirections.actionTopAlbumsFragmentToDetailFragment(
            id,
            name,
            artist
        )
        findNavController().navigate(action)
    }

    private fun getArtistNameAndObserve() {
        val getArtistName = arguments?.getString("name")
        if (getArtistName?.isNotEmpty() == true) observeTopAlbumsBasedOnArtistName(getArtistName)
    }

    private fun observeTopAlbumsBasedOnArtistName(artistName: String) {
        topAlbumsViewModel.getTopAlbumsBasedOnArtist(artistName)

        getTopAlbumsState()
    }

    private fun getTopAlbumsState() {
        viewLifecycleOwner.lifecycleScope.launch {
            topAlbumsViewModel.uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { uiState -> updateUI(uiState) }
        }
    }

    private fun updateUI(uiState: UiState<List<TopAlbum>>) {
        when (uiState) {
            is UiState.Loading -> binding.progressBar.visible()
            is UiState.Success -> onSuccess(uiState.data)
            is UiState.Error -> onError(uiState)
        }
    }

    private fun onSuccess(data: List<TopAlbum>) {
        binding.progressBar.inVisible()
        setAlbums(data)
    }

    private fun onError(uiState: UiState.Error) {
        binding.progressBar.inVisible()
        Toast.makeText(requireContext(), uiState.error, Toast.LENGTH_SHORT).show()
    }

    private fun setAlbums(albums: List<TopAlbum>) {
        topAlbumsAdapter.setData(albums)
    }
}