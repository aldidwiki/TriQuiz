package com.aldidwiki.myquizapp.ui.pregame

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItems
import com.aldidwiki.myquizapp.data.model.QuestionParameter
import com.aldidwiki.myquizapp.data.source.remote.ApiResponse
import com.aldidwiki.myquizapp.databinding.FragmentPreGameBinding
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PreGameFragment : Fragment() {
    private var _binding: FragmentPreGameBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PreGameViewModel>()
    private val args by navArgs<PreGameFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View {
        _binding = FragmentPreGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as AppCompatActivity
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.title = args.categoryItem.name

        var difficulties: String? = null

        binding.edtName.requestFocus()
        binding.btnChangeDifficulties.setOnClickListener {
            val listMenu = listOf("Easy", "Medium", "Hard")
            MaterialDialog(requireContext()).show {
                title(text = "Choose Difficulties")
                listItems(items = listMenu) { _, _, text ->
                    val difficultiesText = "Difficulties : $text"
                    binding.btnChangeDifficulties.text = difficultiesText
                    difficulties = text.toString().toLowerCase(Locale.ROOT)
                }
                lifecycleOwner(viewLifecycleOwner)
            }
        }

        with(binding) {
            btnStart.setOnClickListener {
                if (edtName.check()) {
                    viewModel.clearQuestionEntity()
                    viewModel.getToken().observe(viewLifecycleOwner) { state ->
                        when (state) {
                            is ApiResponse.Success -> {
                                val questionParams = QuestionParameter(
                                        token = state.body?.token!!,
                                        categoryId = args.categoryItem.id,
                                        difficulty = difficulties
                                )

                                val toGame = PreGameFragmentDirections
                                        .actionPreGameFragmentToGameFragment(
                                                edtName.text.toString().trimStart(),
                                                questionParams
                                        )
                                findNavController().navigate(toGame)
                            }
                            is ApiResponse.Loading -> btnStart.startAnimation()
                            is ApiResponse.Error -> {
                                btnStart.revertAnimation()
                                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun TextInputEditText.check(): Boolean {
        return if (this.text.isNullOrBlank()) {
            binding.edtNameLayout.error = "Please enter your name"
            this.addTextChangedListener {
                if (!it.isNullOrBlank()) binding.edtNameLayout.error = null
            }
            false
        } else {
            hideKeyboard()
            true
        }
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = activity?.currentFocus
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            activity?.onBackPressed()
        }
        return true
    }
}