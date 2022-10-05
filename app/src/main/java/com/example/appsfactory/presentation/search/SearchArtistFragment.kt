package com.example.appsfactory.presentation.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.appsfactory.databinding.FragmentArtistSearchBinding
import com.example.appsfactory.presentation.base.BaseFragment
import com.example.appsfactory.presentation.util.hideSoftInput
import com.example.appsfactory.presentation.util.inVisible
import com.example.appsfactory.presentation.util.visible
import com.example.appsfactory.util.NetworkResult
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

    private fun onArtistClicked(artist: com.example.appsfactory.domain.model.artistList.Artist) {
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
                is NetworkResult.Success -> {
                    binding.progressBar.inVisible()
                    submitList(it.data)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    binding.progressBar.inVisible()
                }
                is NetworkResult.Loading -> binding.progressBar.visible()
            }
        }
    }

    private fun submitList(artists: com.example.appsfactory.domain.model.artistList.Artistmatches) {
        searchArtistAdapter.setData(artists.artist)
    }
}