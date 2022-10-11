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
import com.example.appsfactory.databinding.FragmentAlbumInfoBinding
import com.example.appsfactory.presentation.base.BaseFragment
import com.example.appsfactory.presentation.util.gone
import com.example.appsfactory.presentation.util.visible
import com.example.appsfactory.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlbumDetailFragment :
    BaseFragment<FragmentAlbumInfoBinding>(FragmentAlbumInfoBinding::inflate) {

    private val detailViewModel by activityViewModels<DetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAlbumDetail()
        getAlbumDetailState()
    }

    private fun getAlbumDetail() {
        val id = arguments?.getInt("id")
        val albumName = arguments?.getString("albumName").toString()
        val artistName = arguments?.getString("artistName").toString()

        detailViewModel.getAlbumInfo(id!!, albumName, artistName)
    }

    private fun getAlbumDetailState() {
        viewLifecycleOwner.lifecycleScope.launch {
            detailViewModel.uiState.flowWithLifecycle(
                lifecycle, Lifecycle.State.STARTED
            ).collect { state -> updateUI(state) }
        }
    }

    private fun updateUI(uiState: UiState<AlbumInfoEntity>) {
        when (uiState) {
            is UiState.Loading -> binding.progressBar.visible()
            is UiState.Success -> onSuccess(uiState.data)
            is UiState.Error -> onError(uiState.error)
        }
    }

    private fun onSuccess(album: AlbumInfoEntity) {
        binding.apply {
            progressBar.gone()

            album.apply {
                titleTV.text = albumName
                txtArtist.text = artistName
                txtContent.text = wiki

                Glide.with(requireContext()).load(image).into(imageView)
            }
        }
    }

    private fun onError(error: String) {
        binding.progressBar.gone()
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }
}