package com.example.appsfactory.ui.view.top_albums

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.appsfactory.data.local.entity.LocalAlbum
import com.example.appsfactory.data.model.top_albums.Album
import com.example.appsfactory.databinding.FragmentTopAlbumsBinding
import com.example.appsfactory.ui.adapter.TopAlbumAdapter
import com.example.appsfactory.ui.base.BaseFragment
import com.example.appsfactory.util.Resource
import com.example.appsfactory.util.inVisible
import com.example.appsfactory.util.visible
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
        topAlbumsAdapter = TopAlbumAdapter(this::onAlbumClicked, this::onBookmarkClicked)
        binding.recyclerView.adapter = topAlbumsAdapter
    }

    private fun onBookmarkClicked(album: Album) {
        val mAlbum = LocalAlbum(
            id = album.playcount,
            name = album.name,
            artist = album.artist.name,
            image = album.image[2].text,
            url = album.url
        )

        topAlbumsViewModel.onBookmarkClicked(mAlbum)
    }

    private fun onAlbumClicked(album: Album) {
        Toast.makeText(requireContext(), album.name, Toast.LENGTH_SHORT).show()
    }

    private fun getArtistNameAndObserve() {
        //arguments?.getString("artistName")
        val getArtistName = "Justin Bieber"
        if (getArtistName != null) observeTopAlbumsBasedOnArtistName(getArtistName)
    }

    private fun observeTopAlbumsBasedOnArtistName(artistName: String) {
        topAlbumsViewModel.getTopAlbumsBasedOnArtist(artistName).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.inVisible()
                    setAlbums(it.data)
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    binding.progressBar.inVisible()
                }
                is Resource.Loading -> binding.progressBar.visible()
            }
        }
    }

    private fun setAlbums(albums: List<Album>) {
        topAlbumsAdapter.setData(albums)
    }
}