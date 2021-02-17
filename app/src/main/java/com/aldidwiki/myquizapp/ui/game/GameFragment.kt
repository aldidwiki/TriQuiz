package com.aldidwiki.myquizapp.ui.game

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.adapter.SectionsPagerAdapter
import com.aldidwiki.myquizapp.databinding.FragmentGameBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.util.concurrent.TimeUnit

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragmentActivity: AppCompatActivity
    private var timer: CountDownTimer? = null

    companion object {
        private const val TEST = 10000L
        private const val FIVE_MINUTES = 300_000L
        private const val TEN_MINUTES = 600_000L
        private const val ONE_SECOND = 1000L
    }

    private var onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            binding.btnContinue.setOnClickListener {
                binding.viewPager.setCurrentItem(position + 1, true)
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
        setupTimer()
        setupTabLayout()
    }

    private fun setupTimer() {
        timer = object : CountDownTimer(FIVE_MINUTES, ONE_SECOND) {
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
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        with(binding) {
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = "Question ${position + 1}"
            }.attach()
            viewPager.registerOnPageChangeCallback(onPageChangeCallback)
        }
    }
}