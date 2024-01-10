package com.laioffer.spotify.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme(colors = darkColors()){
                    HomeScreen(viewModel, onTap = {
                        Log.d("HomeFragment", "We tapped ${it.name}")
                        val direction = HomeFragmentDirections.actionHomeFragmentToPlaylistFragment(it)
                        findNavController().navigate(directions = direction)
                    })
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.uiState.value.isLoading) {
            viewModel.fetchHomeScreen()
        }
    }
}

