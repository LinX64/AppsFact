package com.example.appsfactory.ui.view.detail

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.appsfactory.databinding.FragmentAlbumDetailBinding
import com.example.appsfactory.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlbumDetailFragment :
    BaseFragment<FragmentAlbumDetailBinding>(FragmentAlbumDetailBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        loadData()
    }

    private fun loadData() {
        val name = arguments?.getString("name")
        val artist = arguments?.getString("artistName")
        val image = arguments?.getString("imageUrl")
        val isBookmarked = arguments?.getBoolean("isBookmarked")

        binding.titleTV.text = name
        binding.txtArtist.text = "Artist: $artist"
        binding.txtBookmarked.text = "Is saved locally? : $isBookmarked"
        Glide.with(requireContext()).load(image).into(binding.imageView)
    }

}