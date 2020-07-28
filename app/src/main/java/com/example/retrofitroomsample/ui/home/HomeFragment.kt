package com.example.retrofitroomsample.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import com.example.retrofitroomsample.R
import com.example.retrofitroomsample.binding.viewBinding
import com.example.retrofitroomsample.databinding.FragmentHomeBinding
import com.example.retrofitroomsample.ui.adapter.UserAdapter
import com.example.retrofitroomsample.viewmodel.DataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel: DataViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeRefreshLayout = binding.swipeRefreshLayout
        val recyclerView = binding.recyclerView
        val adapter = UserAdapter { item, v ->
            viewModel.setItem(item)
            v.transitionName = item.id.toString()
            val extras = FragmentNavigatorExtras(v to item.id.toString())
            findNavController().navigate(R.id.action_home_to_details, null, null, extras)
        }
        adapter.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
        swipeRefreshLayout.isEnabled = false

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            setAdapter(adapter)
        }

        adapter.addLoadStateListener { state ->
            swipeRefreshLayout.isRefreshing = state.refresh is LoadState.Loading
        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.usersFlow.collectLatest { items ->
                adapter.submitData(items)
            }
        }
    }
}