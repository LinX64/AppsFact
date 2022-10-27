/*
 * *
 *  * Created by Mohsen on 10/5/22, 2:45 PM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 10/5/22, 2:41 PM
 *
 */

package com.example.appsfactory.ui.search

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.appsfactory.databinding.FragmentArtistSearchBinding
import com.example.appsfactory.domain.model.artistList.Artist
import com.example.appsfactory.ui.base.BaseFragment
import com.example.appsfactory.ui.util.*
import dagger.hilt.android.AndroidEntryPoint

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
        searchViewModel(artistName)
            .observeWithLifecycle(this) { handleState(it) }
    }

    private fun handleState(result: Any) {
        when (result) {
            is ArtistListState.Loading -> showLoading()
            is ArtistListState.Success -> onSuccess(result)
            is ArtistListState.Error -> onError(result)
        }
    }

    private fun showLoading() {
        binding.progressBar.visible()
    }

    private fun onSuccess(result: ArtistListState.Success) {
        binding.progressBar.gone()
        searchArtistAdapter.submitList(result.artists)
    }

    private fun onError(result: Any) {
        binding.progressBar.gone()
        showSnackBar(requireActivity(), result.toString())
    }
}