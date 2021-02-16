package com.aldidwiki.myquizapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.adapter.CategoryAdapter
import com.aldidwiki.myquizapp.data.source.remote.ApiResponse
import com.aldidwiki.myquizapp.data.source.remote.entity.TriviaCategoriesItem
import com.aldidwiki.myquizapp.databinding.FragmentHomeBinding
import com.aldidwiki.myquizapp.helper.show
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), CategoryAdapter.OnItemClickCallback {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeData()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvCategory.apply {
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = categoryAdapter
        }
        categoryAdapter.setOnItemClickCallback(this)
    }

    private fun subscribeData() {
        homeViewModel.getCategories.observe(viewLifecycleOwner) { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    binding.progressBar.show(false)
                    categoryAdapter.submitList(apiResponse.body.triviaCategories)
                }
                is ApiResponse.Loading -> binding.progressBar.show(true)
                is ApiResponse.Error -> {
                    binding.progressBar.show(false)
                    Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onItemClicked(item: TriviaCategoriesItem) {
        findNavController().navigate(R.id.action_navigation_home_to_preGameFragment)
    }
}