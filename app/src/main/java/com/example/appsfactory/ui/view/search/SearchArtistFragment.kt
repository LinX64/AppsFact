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
import com.example.appsfactory.util.NetworkResult
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
        binding.btnSend.setOnClickListener { searchArtist(it) }
    }

    private fun searchArtist(it: View) {
        it.hideSoftInput()

        val artistName = binding.editText.text.toString()
        setupObserver(artistName)
    }

    private fun setupObserver(artistName: String) {
        searchViewModel.getArtist(artistName).observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    binding.progressBar.inVisible()
                    setArtist(it.data)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    binding.progressBar.inVisible()
                }
                is NetworkResult.Loading -> binding.progressBar.visible()
            }
        }
    }

    private fun setArtist(artists: Artistmatches) {
        searchArtistAdapter.setData(artists.artist)
    }
}