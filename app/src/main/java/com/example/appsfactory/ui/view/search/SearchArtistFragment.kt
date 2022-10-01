package com.example.appsfactory.ui.view.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.appsfactory.data.model.Artistmatches
import com.example.appsfactory.databinding.FragmentArtistSearchBinding
import com.example.appsfactory.ui.adapter.SearchArtistAdapter
import com.example.appsfactory.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchArtistFragment : Fragment() {

    private var _binding: FragmentArtistSearchBinding? = null
    private val binding get() = _binding!!

    private val searchArtistViewModel by activityViewModels<SearchViewModel>()
    private val searchArtistAdapter by lazy { SearchArtistAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtistSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        setupSearch()

        binding.recyclerView.adapter = searchArtistAdapter
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
        searchArtistViewModel.getArtist(artistName).observe(viewLifecycleOwner) {
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