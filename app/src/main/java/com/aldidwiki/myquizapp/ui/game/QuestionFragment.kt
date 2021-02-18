package com.aldidwiki.myquizapp.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.data.source.remote.ApiResponse
import com.aldidwiki.myquizapp.databinding.FragmentQuestionBinding
import com.aldidwiki.myquizapp.helper.decodeHtml
import com.aldidwiki.myquizapp.helper.show
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionFragment : Fragment() {
    private var _binding: FragmentQuestionBinding? = null
    private val viewModel: GameViewModel by viewModels({ requireParentFragment() })
    private val binding get() = _binding!!

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
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeData()
    }

    private fun subscribeData() {
        viewModel.getQuestions().observe(viewLifecycleOwner) { state ->
            when (state) {
                is ApiResponse.Success -> {
                    binding.progressBar.show(false)
                    val questions = state.body
                    questions.map {
                        binding.questionData = it
                        val choice = it.incorrectAnswers.toMutableList()
                        choice.add(it.correctAnswer)
                        setChoiceAnswer(choice, it.correctAnswer)
                    }
                }
                is ApiResponse.Loading -> binding.progressBar.show(true)
                is ApiResponse.Error -> {
                    binding.progressBar.show(false)
                    Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setChoiceAnswer(choice: List<String>, correctAnswer: String) {
        with(binding) {
            choice.shuffled().also {
                btnA.text = decodeHtml(it[0])
                btnB.text = decodeHtml(it[1])
                btnC.text = decodeHtml(it[2])
                btnD.text = decodeHtml(it[3])
            }

            println("debug: $correctAnswer")
            btnA.checkAnswer(decodeHtml(correctAnswer))
            btnB.checkAnswer(decodeHtml(correctAnswer))
            btnC.checkAnswer(decodeHtml(correctAnswer))
            btnD.checkAnswer(decodeHtml(correctAnswer))
        }
    }

    private fun MaterialButton.checkAnswer(correctAnswer: String) {
        this.setOnClickListener {
            this.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            if (this.text == correctAnswer) {
                this.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correctAnswerColor))
                viewModel.setIsCorrect(true)
            } else {
                this.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.wrongAnswerColor))
                viewModel.setIsCorrect(false)
            }

            with(binding) {
                val buttons = listOf(btnA, btnB, btnC, btnD)
                for (b in buttons) {
                    if (b != this@checkAnswer) b.isClickable = false
                }
            }
            viewModel.setHasAnswered(true)
        }
    }
}