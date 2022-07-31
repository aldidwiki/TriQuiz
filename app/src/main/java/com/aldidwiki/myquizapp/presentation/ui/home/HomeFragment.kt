package com.aldidwiki.myquizapp.presentation.ui.home

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
import com.aldidwiki.myquizapp.data.source.remote.network.ApiResponse
import com.aldidwiki.myquizapp.databinding.FragmentHomeBinding
import com.aldidwiki.myquizapp.domain.model.CategoryDomainModel
import com.aldidwiki.myquizapp.presentation.adapter.CategoryAdapter
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), CategoryAdapter.OnItemClickCallback {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var skeleton: Skeleton

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
            skeleton = applySkeleton(R.layout.item_category, itemCount = 10).apply {
                shimmerDurationInMillis = 1000L
            }
        }
        categoryAdapter.setOnItemClickCallback(this)
    }

    private fun subscribeData() {
        homeViewModel.getCategories.observe(viewLifecycleOwner) { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    skeleton.showOriginal()
                    categoryAdapter.submitList(apiResponse.body)
                }
                is ApiResponse.Loading -> skeleton.showSkeleton()
                is ApiResponse.Error ->
                    Toast.makeText(activity, resources.getString(R.string.error_message), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemClicked(item: CategoryDomainModel) {
        val toPreGame = HomeFragmentDirections.actionNavigationHomeToPreGameFragment(item)
        findNavController().navigate(toPreGame)
    }
}