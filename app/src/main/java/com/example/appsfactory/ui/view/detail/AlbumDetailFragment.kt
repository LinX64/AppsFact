package com.example.appsfactory.ui.view.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.appsfactory.data.model.albumInfo.Album
import com.example.appsfactory.databinding.FragmentAlbumDetailBinding
import com.example.appsfactory.ui.base.BaseFragment
import com.example.appsfactory.util.Resource
import com.example.appsfactory.util.inVisible
import com.example.appsfactory.util.visible
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
        val albumName = arguments?.getString("name").toString()
        val artist = arguments?.getString("artistName").toString()

        detailViewModel.getAlbumInfo(albumName, artist).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> onSuccess(it.data)
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    println("Error: ${it.error}")
                    binding.progressBar.inVisible()
                }
                is Resource.Loading -> binding.progressBar.visible()
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

}