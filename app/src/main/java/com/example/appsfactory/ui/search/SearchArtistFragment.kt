/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:45 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:41 PM
 *
 */

package com.example.appsfactory.ui.search

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.appsfactory.databinding.FragmentArtistSearchBinding
import com.example.appsfactory.domain.model.artistList.Artist
import com.example.appsfactory.ui.base.BaseFragment
import com.example.appsfactory.ui.util.*
import com.example.appsfactory.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchArtistFragment :
    BaseFragment<FragmentArtistSearchBinding>(FragmentArtistSearchBinding::inflate) {

    private val searchViewModel by viewModels<SearchViewModel>()
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

            val artistName = binding.editText.text
            when {
                artistName.isNullOrBlank() -> showSnackBar(
                    requireActivity(),
                    "Please enter artist name"
                )
                else -> getArtistByName(artistName.toString())
            }
        }
    }

    private fun getArtistByName(artistName: String) {
        searchViewModel(artistName).observeWithLifecycle(this) { updateUI(it) }
    }

    private fun updateUI(uiState: UiState<List<Artist>>) {
        when (uiState) {
            is UiState.Loading -> onLoading()
            is UiState.Success -> onSuccess(uiState.data)
            is UiState.Error -> onError(uiState.error)
        }
    }

    private fun onSuccess(artistList: List<Artist>) {
        hideLoading()
        searchArtistAdapter.submitList(artistList)
    }

    private fun onLoading() {
        binding.progressBar.visible()
    }

    private fun hideLoading() {
        binding.progressBar.gone()
    }

    private fun onError(message: String) {
        hideLoading()
        showSnackBar(requireActivity(), message)
    }
}