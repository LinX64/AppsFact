/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:45 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:41 PM
 *
 */

package com.example.appsfactory.presentation.search

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.appsfactory.databinding.FragmentArtistSearchBinding
import com.example.appsfactory.domain.model.artistList.Artist
import com.example.appsfactory.presentation.base.BaseFragment
import com.example.appsfactory.presentation.util.gone
import com.example.appsfactory.presentation.util.hideSoftInput
import com.example.appsfactory.presentation.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchArtistFragment :
    BaseFragment<FragmentArtistSearchBinding>(FragmentArtistSearchBinding::inflate) {

    private val searchViewModel by activityViewModels<SearchViewModel>()
    private lateinit var searchArtistAdapter: SearchAdapter

    override fun setupUI() {
        super.setupUI()

        setupSearch()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        searchArtistAdapter = SearchAdapter(this::onArtistClicked)
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
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel(artistName)
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { result -> handleState(result) }
        }
    }

    private fun handleState(result: Any) {
        when (result) {
            is ArtistListState.Loading -> binding.progressBar.visible()
            is ArtistListState.Success -> onSuccess(result)
            is ArtistListState.Error -> onError(result)
        }
    }

    private fun onSuccess(result: ArtistListState.Success) {
        binding.progressBar.gone()
        searchArtistAdapter.submitList(result.artists)
    }

    private fun onError(result: Any) {
        binding.progressBar.gone()
        Toast.makeText(
            requireContext(),
            result.toString(),
            Toast.LENGTH_SHORT
        ).show()
    }
}