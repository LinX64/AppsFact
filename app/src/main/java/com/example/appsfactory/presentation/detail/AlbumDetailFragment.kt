package com.example.appsfactory.presentation.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.appsfactory.databinding.FragmentAlbumDetailBinding
import com.example.appsfactory.domain.model.albumInfo.Album
import com.example.appsfactory.presentation.base.BaseFragment
import com.example.appsfactory.presentation.util.inVisible
import com.example.appsfactory.presentation.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumDetailFragment :
    BaseFragment<FragmentAlbumDetailBinding>(FragmentAlbumDetailBinding::inflate) {

    private val detailViewModel by activityViewModels<DetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
    }

    private fun setupObserver() {
        val artistName = arguments?.getString("artistName").toString()
        val albumName = arguments?.getString("albumName").toString()

        lifecycleScope.launchWhenCreated {
            detailViewModel.getAlbumInfo(albumName, artistName).observe(viewLifecycleOwner) { uiState ->
                when (uiState) {
                    is AlbumUiState.Loading -> binding.progressBar.visible()
                    is AlbumUiState.Success -> onSuccess(uiState.album)
                    is AlbumUiState.Error -> onError(uiState)
                }
            }
        }
    }

    private fun onSuccess(album: Album) {
        binding.apply {
            progressBar.inVisible()

            album.apply {
                titleTV.text = name
                txtArtist.text = artist
                txtArtist.text = tracks.track[0].artist.name
                txtContent.text = wiki.content

                Glide.with(requireContext()).load(image[3].text).into(imageView)
            }
        }
    }

    private fun onError(uiState: AlbumUiState.Error) {
        Toast.makeText(requireContext(), uiState.exception, Toast.LENGTH_SHORT).show()
        binding.progressBar.inVisible()
    }

}