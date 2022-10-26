/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:44 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:37 PM
 *
 */

package com.example.appsfactory.presentation.info

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.appsfactory.data.source.local.entity.AlbumInfoEntity
import com.example.appsfactory.databinding.FragmentAlbumInfoBinding
import com.example.appsfactory.presentation.base.BaseFragment
import com.example.appsfactory.presentation.util.gone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AlbumInfoFragment :
    BaseFragment<FragmentAlbumInfoBinding>(FragmentAlbumInfoBinding::inflate) {

    private val detailViewModel by activityViewModels<InfoViewModel>()

    override fun setupUI() {
        super.setupUI()

        getAlbumDetail()
    }

    private fun getAlbumDetail() {
        val id = arguments?.getInt("id")
        val albumName = arguments?.getString("albumName").toString()
        val artistName = arguments?.getString("artistName").toString()

        id ?: return

        viewLifecycleOwner.lifecycleScope.launch {
            detailViewModel(id, albumName, artistName)
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { state -> updateUI(state) }
        }
    }

    private fun updateUI(albumState: AlbumInfoState) {
        if (albumState is AlbumInfoState.Success) {
            val albumInfo = albumState.data
            onSuccess(albumInfo)
        } else if (albumState is AlbumInfoState.Error) onError(albumState.message)

        binding.progressBar.isVisible = albumState is AlbumInfoState.Loading
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