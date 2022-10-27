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
        topAlbumsViewModel.onBookmarkRemoveClicked(album)
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

    private fun updateUi(topAlbumsState: TopAlbumsState) {
        when (topAlbumsState) {
            is TopAlbumsState.Loading -> showLoading()
            is TopAlbumsState.Success -> submitTopAlbums(topAlbumsState.data)
            is TopAlbumsState.Error -> showError(topAlbumsState.message)
        }
    }

    private fun showLoading() {
        binding.progressBar.visible()
    }

    private fun submitTopAlbums(albums: List<TopAlbum>) {
        binding.progressBar.gone()
        topAlbumsAdapter.submitList(albums)
    }

    private fun showError(message: String) {
        binding.progressBar.gone()
        showSnackBar(requireActivity(), message)
    }
}