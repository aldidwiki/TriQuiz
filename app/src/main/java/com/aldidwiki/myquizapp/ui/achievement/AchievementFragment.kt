package com.aldidwiki.myquizapp.ui.achievement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.adapter.FinalResultAdapter
import com.aldidwiki.myquizapp.data.source.local.entity.QuestionEntity
import com.aldidwiki.myquizapp.databinding.FragmentAchievementBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AchievementFragment : Fragment() {
    private var _binding: FragmentAchievementBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var finalResultAdapter: FinalResultAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAchievementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as AppCompatActivity
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.btnHome.setOnClickListener {
            findNavController().navigate(R.id.action_achievementFragment_to_navigation_home)
        }

        setupNestedScroll()
        setupRecyclerView()
        initData()
    }

    private fun setupNestedScroll() {
        binding.nsv.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY) binding.btnHome.hide()
            else binding.btnHome.show()
        })
    }

    private fun initData() {
        val dummy = mutableListOf<QuestionEntity>()
        for (i in 0..5) {
            dummy.add(QuestionEntity(
                    "What airline was the owner of the plane that crashed off the coast of Nova Scotia in 1998?",
                    "The Name of a Warner Brothers Cartoon Character"
            ))
        }
        finalResultAdapter.setData(dummy)
    }

    private fun setupRecyclerView() {
        binding.includeUserResult.rvFinalResult.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = finalResultAdapter
        }
    }
}