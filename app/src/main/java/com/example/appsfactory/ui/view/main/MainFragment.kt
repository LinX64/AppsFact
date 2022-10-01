package com.example.appsfactory.ui.view.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.appsfactory.R
import com.example.appsfactory.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private val mainViewModel by activityViewModels<MainViewModel>()
    //private val musicsAdapter by lazy { MusicsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        setupUI()
        setupObserver()
    }

    private fun setupUI() {
       // binding.recyclerView.adapter = musicsAdapter
    }

    private fun setupObserver() {
        /*mainViewModel.questionsResponse.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    binding.progressBar.inVisible()
                    setQuestions(it.data)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    binding.progressBar.inVisible()
                }
                is NetworkResult.Loading -> binding.progressBar.visible()
            }
        }*/
    }


}