package com.aldidwiki.myquizapp.ui.game

import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.adapter.SectionsPagerAdapter
import com.aldidwiki.myquizapp.databinding.FragmentGameBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragmentActivity: AppCompatActivity
    private var timer: CountDownTimer? = null
    private val viewModel: GameViewModel by viewModels()

    @Inject
    lateinit var prefs: SharedPreferences

    companion object {
        private const val TEST = 10000L
        private const val THREE_MINUTES = 180_000L
        private const val ONE_SECOND = 1000L
    }

    private var onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.btnContinue.setOnClickListener {
                binding.viewPager.setCurrentItem(position + 1, true)
            }
            binding.btnContinue.isEnabled = false
            if (position + 1 == 5) {
                binding.btnContinue.text = "Finish"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentActivity = activity as AppCompatActivity

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                timer?.let {
                    MaterialDialog(requireContext()).show {
                        title(text = "Confirmation")
                        message(text = "Time is still available, are you sure want to exit?")
                        positiveButton(text = "Yes") { findNavController().navigateUp() }
                        negativeButton(text = "No")
                        lifecycleOwner(viewLifecycleOwner)
                    }
                } ?: findNavController().navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
        fragmentActivity.supportActionBar?.show()
        binding.viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentActivity.supportActionBar?.hide()
        subscribeData()
        setupTimer()
        setupTabLayout()
    }

    private fun subscribeData() {
        val token = prefs.getString("token", "token") as String
        viewModel.setToken(token)
        println("debug: $token")

        viewModel.answeredCount.observe(viewLifecycleOwner) {
            val answeredQuestion = "Answered : $it/5"
            binding.tvQuestion.text = answeredQuestion
            binding.btnContinue.isEnabled = it != 0
        }

        viewModel.totalScore.observe(viewLifecycleOwner) {
            val score = "Score : $it"
            binding.tvScore.text = score
        }

        viewModel.correctAnswer.observe(viewLifecycleOwner) {
            val correct = "Correct : $it"
            binding.tvCorrectAnswers.text = correct
        }

        viewModel.incorrectAnswer.observe(viewLifecycleOwner) {
            val incorrect = "Wrong : $it"
            binding.tvWrongAnswers.text = incorrect
        }
    }

    private fun setupTimer() {
        timer = object : CountDownTimer(THREE_MINUTES, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                val mod = TimeUnit.MINUTES.toSeconds(minutes)
                binding.tvTime.text = String.format("%d:%d", minutes, seconds - mod)
            }

            override fun onFinish() {
                MaterialDialog(requireContext()).show {
                    title(text = "Time's Up!")
                    cancelable(false)
                    cancelOnTouchOutside(false)
                    icon(R.drawable.ic_access_alarms)
                    message(text = "You running out time, please try again later")
                    positiveButton(text = "Continue") { findNavController().navigateUp() }
                    lifecycleOwner(viewLifecycleOwner)
                }
            }
        }.start()
    }

    private fun setupTabLayout() {
        with(binding) {
            viewPager.isUserInputEnabled = false
            viewPager.offscreenPageLimit = 4
            viewPager.adapter = SectionsPagerAdapter(this@GameFragment)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = "Question ${position + 1}"
                tab.view.isClickable = false
            }.attach()
            viewPager.registerOnPageChangeCallback(onPageChangeCallback)
        }
    }
}