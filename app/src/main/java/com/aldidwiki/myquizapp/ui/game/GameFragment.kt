package com.aldidwiki.myquizapp.ui.game

import android.content.SharedPreferences
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
import androidx.viewpager2.widget.ViewPager2
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.adapter.SectionsPagerAdapter
import com.aldidwiki.myquizapp.databinding.FragmentGameBinding
import com.aldidwiki.myquizapp.helper.Constant
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragmentActivity: AppCompatActivity
    private val viewModel: GameViewModel by viewModels()
    private var toAchievement: NavDirections? = null

    @Inject
    lateinit var prefs: SharedPreferences

    private var onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.btnContinue.setOnClickListener {
                binding.viewPager.setCurrentItem(position + 1, true)
                viewModel.updateAnsweredCount(viewModel.isCorrect)
                viewModel.insertTempQuestion()
            }

            viewModel.setHasAnswered(false)
            if (position + 1 == Constant.QUESTION_COUNT) binding.btnContinue.text = "Finish"
        }
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            MaterialDialog(requireContext()).show {
                title(text = "Confirmation")
                message(text = "Time is still available, are you sure want to end the quiz?")
                positiveButton(text = "Yes") {
                    toAchievement?.let { findNavController().navigate(it) }
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
        val token = prefs.getString("token", "token") as String
        viewModel.setToken(token)
        viewModel.initUser()

        viewModel.user.observe(viewLifecycleOwner) {
            toAchievement = GameFragmentDirections.actionGameFragmentToAchievementFragment(it)
        }

        viewModel.eventTimeFinished.observe(viewLifecycleOwner) { hasFinished ->
            if (hasFinished)
                MaterialDialog(requireContext()).show {
                    title(text = "Time's Up!")
                    cancelable(false)
                    cancelOnTouchOutside(false)
                    icon(R.drawable.ic_access_alarms)
                    message(text = "You running out time, you can retake the quiz")
                    positiveButton(text = "Continue") {
                        toAchievement?.let { findNavController().navigate(it) }
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