package com.aldidwiki.myquizapp.presentation.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.presentation.adapter.SectionsPagerAdapter
import com.aldidwiki.myquizapp.databinding.FragmentGameBinding
import com.aldidwiki.myquizapp.helper.Constant
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragmentActivity: AppCompatActivity
    private val viewModel: GameViewModel by viewModels()
    private var toAchievement: NavDirections? = null
    private val args by navArgs<GameFragmentArgs>()

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.btnContinue.setOnClickListener {
                binding.viewPager.setCurrentItem(position + 1, true)
                with(viewModel) {
                    updateAnsweredCount(isCorrect)
                    insertTempQuestion()
                }
            }

            viewModel.setHasAnswered(false)
            if (position + 1 == Constant.QUESTION_COUNT)
                binding.btnContinue.text = resources.getString(R.string.finish)
        }
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            MaterialDialog(requireContext()).show {
                title(text = resources.getString(R.string.confirmation))
                message(text = resources.getString(R.string.exit_confirmation))
                positiveButton(text = "Yes") {
                    toAchievement?.let { findNavController().navigate(it) }
                            ?: findNavController().navigateUp()
                }
                negativeButton(text = "No")
                lifecycleOwner(viewLifecycleOwner)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentActivity = activity as AppCompatActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentActivity.supportActionBar?.show()
        binding.viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentActivity.supportActionBar?.hide()
        subscribeData()
        setupTabLayout()
    }

    private fun subscribeData() {
        viewModel.userName = args.userName
        viewModel.setQuestionParams(args.questionParams)

        viewModel.user.observe(viewLifecycleOwner) {
            toAchievement = GameFragmentDirections.actionGameFragmentToAchievementFragment(it)
        }

        viewModel.eventTimeFinished.observe(viewLifecycleOwner) { hasFinished ->
            if (hasFinished)
                MaterialDialog(requireContext()).show {
                    title(text = resources.getString(R.string.time_up))
                    cancelable(false)
                    cancelOnTouchOutside(false)
                    icon(R.drawable.ic_access_alarms)
                    message(text = resources.getString(R.string.time_up_information))
                    positiveButton(text = "Continue") {
                        toAchievement?.let { findNavController().navigate(it) }
                                ?: findNavController().navigateUp()
                    }
                    lifecycleOwner(viewLifecycleOwner)
                }
            else requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        }

        viewModel.onLastQuestion.observe(viewLifecycleOwner) { isLastQuestion ->
            if (isLastQuestion)
                lifecycleScope.launch {
                    binding.btnContinue.startAnimation()
                    delay(1500L)
                    toAchievement?.let { findNavController().navigate(it) }
                }
        }
    }

    private fun setupTabLayout() {
        with(binding) {
            viewPager.isUserInputEnabled = false
            viewPager.offscreenPageLimit = Constant.QUESTION_COUNT - 1
            viewPager.adapter = SectionsPagerAdapter(this@GameFragment)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = "Question ${position + 1}"
                tab.view.isClickable = false
            }.attach()
            viewPager.registerOnPageChangeCallback(onPageChangeCallback)
        }
    }
}