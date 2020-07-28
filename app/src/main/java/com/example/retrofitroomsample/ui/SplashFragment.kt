package com.example.retrofitroomsample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.retrofitroomsample.R
import com.example.retrofitroomsample.viewmodel.DataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val viewModel: DataViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.isLoggedIn()) findNavController().navigate(R.id.action_splash_to_home)
        else findNavController().navigate(R.id.action_splash_to_auth)
    }

}