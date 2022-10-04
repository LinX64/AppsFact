package com.example.appsfactory.ui.view.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.appsfactory.data.model.artistList.Artist
import com.example.appsfactory.data.model.artistList.Artistmatches
import com.example.appsfactory.databinding.FragmentArtistSearchBinding
import com.example.appsfactory.ui.adapter.SearchArtistAdapter
import com.example.appsfactory.ui.base.BaseFragment
import com.example.appsfactory.ui.viewmodel.SearchViewModel
import com.example.appsfactory.util.Resource
import com.example.appsfactory.util.hideSoftInput
import com.example.appsfactory.util.inVisible
import com.example.appsfactory.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchArtistFragment :
    BaseFragment<FragmentArtistSearchBinding>(FragmentArtistSearchBinding::inflate) {

    private val searchViewModel by activityViewModels<SearchViewModel>()
    private lateinit var searchArtistAdapter: SearchArtistAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        setupSearch()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        searchArtistAdapter = SearchArtistAdapter(this::onArtistClicked)
        binding.recyclerView.adapter = searchArtistAdapter
    }

    private fun onArtistClicked(artist: Artist) {
        val action =
            SearchArtistFragmentDirections.actionSearchArtistFragmentToTopAlbumsFragment(artist.name)
        findNavController().navigate(action)
    }

    private fun setupSearch() {
        binding.btnSend.setOnClickListener {
            it.hideSoftInput()
            val artistName = binding.editText.text.toString()

            if (artistName.isNotEmpty())
                setupObserver(artistName)
            else Toast.makeText(requireContext(), "Please enter artist name", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setupObserver(artistName: String = "Justin Bieber") {
        searchViewModel.getArtist(artistName).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.inVisible()
                    submitList(it.data)
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    binding.progressBar.inVisible()
                }
                is Resource.Loading -> binding.progressBar.visible()
            }
        }
    }

    private fun submitList(artists: Artistmatches) {
        searchArtistAdapter.setData(artists.artist)
    }
}