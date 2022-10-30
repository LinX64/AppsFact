/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:48 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:47 PM
 *
 */

package com.example.appsfactory.ui.top_albums

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.appsfactory.databinding.FragmentTopAlbumsBinding
import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.ui.base.BaseFragment
import com.example.appsfactory.ui.util.gone
import com.example.appsfactory.ui.util.observeWithLifecycle
import com.example.appsfactory.ui.util.showSnackBar
import com.example.appsfactory.ui.util.visible
import com.example.appsfactory.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopAlbumsFragment :
    BaseFragment<FragmentTopAlbumsBinding>(FragmentTopAlbumsBinding::inflate) {

    private val topAlbumsViewModel by viewModels<TopAlbumsViewModel>()
    private lateinit var topAlbumsAdapter: TopAlbumAdapter

    override fun setupUI() {
        super.setupUI()

        setupRecyclerView()
        getArtistNameAndUpdateUI()
    }

    private fun setupRecyclerView() {
        topAlbumsAdapter = TopAlbumAdapter(
            this::onAlbumClicked, this::onBookmarkClicked, this::onBookmarkRemoveClicked
        )
        binding.recyclerViewTopAlbums.adapter = topAlbumsAdapter
    }

    private fun onBookmarkClicked(album: TopAlbum) {
        topAlbumsViewModel.onBookmarkClicked(album)
    }

    private fun onBookmarkRemoveClicked(album: TopAlbum) {
        topAlbumsViewModel.onBookmarkRemove(album)
    }

    private fun onAlbumClicked(album: TopAlbum) {
        val id = album.playcount
        val name = album.name
        val artist = album.artist.name

        val action = TopAlbumsFragmentDirections.actionTopAlbumsFragmentToDetailFragment(
            id, name, artist
        )
        findNavController().navigate(action)
    }

    private fun getArtistNameAndUpdateUI() {
        topAlbumsViewModel
            .topAlbumsState
            .observeWithLifecycle(this) { updateUi(it) }
    }

    private fun updateUi(topAlbumsState: UiState<List<TopAlbum>>) {
        when (topAlbumsState) {
            is UiState.Loading -> onLoading()
            is UiState.Success -> submitTopAlbums(topAlbumsState.data)
            is UiState.Error -> showError(topAlbumsState.error)
        }
    }

    private fun onLoading() {
        showProgressBar()
    }

    private fun submitTopAlbums(albums: List<TopAlbum>) {
        hideProgressBar()
        topAlbumsAdapter.submitList(albums)
    }

    private fun showError(message: String) {
        hideProgressBar()
        showSnackBar(requireActivity(), message)
    }

    private fun hideProgressBar() {
        binding.progressBar.gone()
    }

    private fun showProgressBar() {
        binding.progressBar.visible()
    }
}