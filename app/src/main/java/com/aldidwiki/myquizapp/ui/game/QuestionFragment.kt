package com.aldidwiki.myquizapp.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aldidwiki.myquizapp.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment() {
    private var _binding: FragmentQuestionBinding? = null
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

        binding.tvHello.text = "This is Fragment $position"
    }
}