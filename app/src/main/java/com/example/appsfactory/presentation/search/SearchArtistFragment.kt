/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:45 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:41 PM
 *
 */

package com.example.appsfactory.presentation.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appsfactory.databinding.FragmentArtistSearchBinding
import com.example.appsfactory.domain.model.artistList.Artist
import com.example.appsfactory.domain.model.artistList.Artistmatches
import com.example.appsfactory.presentation.base.BaseFragment
import com.example.appsfactory.util.UiState
import com.example.appsfactory.util.hideSoftInput
import com.example.appsfactory.util.inVisible
import com.example.appsfactory.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                getArtistByName(artistName)
            else Toast.makeText(requireContext(), "Please enter artist name", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun getArtistByName(artistName: String) {
        searchViewModel.getArtist(artistName)

        getArtistByNameState()
    }

    private fun getArtistByNameState() {
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel
                .uiState
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { uiState -> updateUI(uiState) }
        }
    }

    private fun updateUI(uiState: UiState<Artistmatches>) {
        when (uiState) {
            is UiState.Loading -> binding.progressBar.visible()
            is UiState.Success -> onSuccess(uiState.data)
            is UiState.Error -> onError(uiState)
        }
    }

    private fun onSuccess(data: Artistmatches) {
        binding.progressBar.inVisible()
        submitList(data)
    }

    private fun onError(uiState: UiState.Error) {
        Toast.makeText(requireContext(), uiState.error, Toast.LENGTH_SHORT).show()
        binding.progressBar.inVisible()
    }

    private fun submitList(artists: Artistmatches) {
        searchArtistAdapter.setData(artists.artist)
    }
}