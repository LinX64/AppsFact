package com.example.appsfactory.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.appsfactory.data.source.local.entity.LocalAlbum
import com.example.appsfactory.databinding.FragmentMainBinding
import com.example.appsfactory.presentation.base.BaseFragment
import com.example.appsfactory.presentation.util.gone
import com.example.appsfactory.presentation.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val mainViewModel by activityViewModels<MainViewModel>()
    private lateinit var topAlbumsAdapter: TopAlbumsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        topAlbumsAdapter = TopAlbumsAdapter(this::onAlbumClicked)
        binding.recyclerViewMain.adapter = topAlbumsAdapter
    }

    private fun onAlbumClicked(album: LocalAlbum) {
        val name = album.name
        val artistName = album.artist

        val action =
            MainFragmentDirections.actionMainFragmentToDetailFragment(name, artistName)
        findNavController().navigate(action)
    }

    private fun setupObserver() {
        mainViewModel.getBookmarkedAlbums().observe(viewLifecycleOwner) { submitList(it) }
    }

    private fun submitList(localAlbums: List<LocalAlbum>) {
        if (localAlbums.isEmpty()) {
            binding.recyclerViewMain.gone()
            binding.emptyView.emptyViewLayout.visible()
        } else {
            binding.recyclerViewMain.visible()
            binding.emptyView.emptyViewLayout.gone()
            topAlbumsAdapter.setData(localAlbums)
        }
    }
}