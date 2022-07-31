package com.aldidwiki.myquizapp.presentation.ui.achievement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.databinding.FragmentAchievementBinding
import com.aldidwiki.myquizapp.helper.Constant
import com.aldidwiki.myquizapp.presentation.adapter.AchievementAdapter
import com.robinhood.ticker.TickerUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AchievementFragment : Fragment() {
    private var _binding: FragmentAchievementBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AchievementViewModel>()
    private val args by navArgs<AchievementFragmentArgs>()

    @Inject
    lateinit var achievementAdapter: AchievementAdapter

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

        initData()
        initRecyclerView()
        setupNestedScroll()

        binding.btnHome.setOnClickListener {
            findNavController().navigate(R.id.action_achievementFragment_to_navigation_home)
        }
    }

    private fun initRecyclerView() {
        binding.includeUserResult.rvFinalResult.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = achievementAdapter
        }
    }

    private fun initData() {
        viewModel.insertUser(args.userDomainModel)

        viewModel.getTempResults.observe(viewLifecycleOwner) {
            achievementAdapter.submitList(it)
        }

        with(binding.includeUserResult) {
            result = args.userDomainModel
            questionCount = Constant.QUESTION_COUNT

            tvScore.setCharacterLists(TickerUtils.provideNumberList())
            tvScore.text = args.userDomainModel.totalScore.toString()
        }
    }

    private fun setupNestedScroll() {
        binding.nsv.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY) binding.btnHome.hide()
            else binding.btnHome.show()
        })
    }
}