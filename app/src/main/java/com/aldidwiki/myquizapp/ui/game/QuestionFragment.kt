package com.aldidwiki.myquizapp.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.databinding.FragmentQuestionBinding
import com.google.android.material.button.MaterialButton

class QuestionFragment : Fragment() {
    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!
    private val correctAnswer = "A"

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(position: Int): QuestionFragment {
            return QuestionFragment().apply {
                arguments = Bundle().apply { putInt(ARG_SECTION_NUMBER, position) }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(ARG_SECTION_NUMBER, 0)

        with(binding) {
            val listAnswers = listOf("A", "B", "C", "D")
            listAnswers.shuffled().also {
                btnA.text = it[0]
                btnB.text = it[1]
                btnC.text = it[2]
                btnD.text = it[3]
            }

            btnA.checkAnswer()
            btnB.checkAnswer()
            btnC.checkAnswer()
            btnD.checkAnswer()
        }
    }

    private fun MaterialButton.checkAnswer() {
        this.setOnClickListener {
            this.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            if (this.text == correctAnswer) {
                this.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctAnswerColor))
            } else {
                this.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.wrongAnswerColor))
            }

            with(binding) {
                val buttons = listOf(btnA, btnB, btnC, btnD)
                for (b in buttons) {
                    if (b != this@checkAnswer) b.isClickable = false
                }
            }
        }
    }
}