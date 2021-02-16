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
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.databinding.FragmentGameBinding
import java.util.concurrent.TimeUnit

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private var timer: CountDownTimer? = null
    private lateinit var fragmentActivity: AppCompatActivity

    companion object {
        private const val TEST = 10000L
        private const val FIVE_MINUTES = 300_000L
        private const val TEN_MINUTES = 600_000L
        private const val ONE_SECOND = 1000L
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
        timer = null
        _binding = null
        fragmentActivity.supportActionBar?.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentActivity.supportActionBar?.hide()

        timer = object : CountDownTimer(TEST, ONE_SECOND) {
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
                    positiveButton(text = "Continue") {
                        findNavController().navigateUp()
                    }
                    lifecycleOwner(viewLifecycleOwner)
                }
            }
        }.start()
    }
}