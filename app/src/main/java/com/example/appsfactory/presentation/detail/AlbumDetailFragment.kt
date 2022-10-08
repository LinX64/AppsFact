/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:44 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:37 PM
 *
 */

package com.example.appsfactory.presentation.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.appsfactory.data.source.local.entity.AlbumInfoEntity
import com.example.appsfactory.databinding.FragmentAlbumDetailBinding
import com.example.appsfactory.presentation.base.BaseFragment
import com.example.appsfactory.presentation.util.inVisible
import com.example.appsfactory.presentation.util.visible
import com.example.appsfactory.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlbumDetailFragment :
    BaseFragment<FragmentAlbumDetailBinding>(FragmentAlbumDetailBinding::inflate) {

    private val detailViewModel by activityViewModels<DetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAlbumDetail()
        getAlbumDetailState()
    }

    private fun getAlbumDetail() {
        val albumName = arguments?.getString("albumName").toString()
        val artistName = arguments?.getString("artistName").toString()

        detailViewModel.getAlbumInfo(albumName, artistName)
    }

    private fun getAlbumDetailState() {
        viewLifecycleOwner.lifecycleScope.launch {
            detailViewModel.uiState.flowWithLifecycle(
                viewLifecycleOwner.lifecycle,
                Lifecycle.State.STARTED
            ).collect {
                when (it) {
                    is UiState.Loading -> binding.progressBar.visible()
                    is UiState.Success -> onSuccess(it.data)
                    is UiState.Error -> onError(it.error)
                }
            }
        }
    }

    private fun onSuccess(album: AlbumInfoEntity) {
        binding.apply {
            progressBar.inVisible()

            album.apply {
                titleTV.text = albumName
                txtArtist.text = artistName
                txtArtist.text = tracks[0].toString()
                txtContent.text = wiki

                Glide.with(requireContext()).load(image[0]).into(imageView)
            }
        }
    }

    private fun onError(error: String) {
        binding.emptyView.emptyViewLayout.visible()
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }
}