/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:44 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:37 PM
 *
 */

package com.example.appsfactory.ui.info

import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.appsfactory.data.source.local.entity.AlbumInfoEntity
import com.example.appsfactory.databinding.FragmentAlbumInfoBinding
import com.example.appsfactory.ui.base.BaseFragment
import com.example.appsfactory.ui.util.gone
import com.example.appsfactory.ui.util.observeWithLifecycle
import com.example.appsfactory.ui.util.showSnackBar
import com.example.appsfactory.ui.util.visible
import com.example.appsfactory.util.UiState
import dagger.hilt.android.AndroidEntryPoint

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

        detailViewModel(id, albumName, artistName)
            .observeWithLifecycle(this) { handleState(it) }
    }

    private fun handleState(it: UiState<AlbumInfoEntity>) {
        when (it) {
            is UiState.Success -> onSuccess(it.data)
            is UiState.Error -> onError(it.error, it.data)
            is UiState.Loading -> onLoading()
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

    private fun onError(error: String, data: AlbumInfoEntity?) {
        if (error.isNotEmpty()) {
            hideProgressBar()
            showSnackBar(requireActivity(), error)

            data?.let { onSuccess(it) }
        }
    }

    private fun onLoading() {
        binding.progressBar.visible()
    }

    private fun hideProgressBar() {
        binding.progressBar.gone()
    }
}