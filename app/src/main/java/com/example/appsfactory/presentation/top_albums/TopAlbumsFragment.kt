package com.example.appsfactory.presentation.top_albums

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appsfactory.databinding.FragmentTopAlbumsBinding
import com.example.appsfactory.domain.model.top_albums.TopAlbum
import com.example.appsfactory.presentation.base.BaseFragment
import com.example.appsfactory.presentation.util.inVisible
import com.example.appsfactory.presentation.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopAlbumsFragment :
    BaseFragment<FragmentTopAlbumsBinding>(FragmentTopAlbumsBinding::inflate) {

    private val topAlbumsViewModel by activityViewModels<TopAlbumsViewModel>()
    private lateinit var topAlbumsAdapter: TopAlbumAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        setupRecyclerView()

        getArtistNameAndObserve()
    }

    private fun setupRecyclerView() {
        topAlbumsAdapter = TopAlbumAdapter(
            this::onAlbumClicked,
            this::onBookmarkClicked,
            this::onBookmarkRemoveClicked
        )
        binding.recyclerViewTopAlbums.adapter = topAlbumsAdapter
    }

    private fun onBookmarkClicked(album: TopAlbum) {
        topAlbumsViewModel.onBookmarkClicked(album)
        Toast.makeText(requireContext(), "Bookmarked!", Toast.LENGTH_SHORT).show()
    }

    private fun onBookmarkRemoveClicked(album: TopAlbum) {
        topAlbumsViewModel.onBookmarkRemoveClicked(album)
    }

    private fun onAlbumClicked(album: TopAlbum) {
        val name = album.name
        val artist = album.artist.name

        val action = TopAlbumsFragmentDirections.actionTopAlbumsFragmentToDetailFragment(
            name,
            artist
        )
        findNavController().navigate(action)
    }

    private fun getArtistNameAndObserve() {
        val getArtistName = arguments?.getString("name")
        if (getArtistName?.isNotEmpty() == true) observeTopAlbumsBasedOnArtistName(getArtistName)
    }

    private fun observeTopAlbumsBasedOnArtistName(artistName: String) {
        lifecycleScope.launchWhenCreated {
            topAlbumsViewModel.getTopAlbumsBasedOnArtist(artistName)
                .observe(viewLifecycleOwner) { uiState ->
                    when (uiState) {
                        is TopAlbumsUiState.Loading -> binding.progressBar.visible()
                        is TopAlbumsUiState.Success -> onSuccess(uiState)
                        is TopAlbumsUiState.Error -> onError(uiState)
                    }
                }
        }
    }

    private fun onSuccess(uiState: TopAlbumsUiState.Success) {
        binding.progressBar.inVisible()
        setAlbums(uiState.albums)
    }

    private fun onError(uiState: TopAlbumsUiState.Error) {
        binding.progressBar.inVisible()
        Toast.makeText(requireContext(), uiState.exception, Toast.LENGTH_SHORT).show()
    }

    private fun setAlbums(albums: List<TopAlbum>) {
        topAlbumsAdapter.setData(albums)
    }
}