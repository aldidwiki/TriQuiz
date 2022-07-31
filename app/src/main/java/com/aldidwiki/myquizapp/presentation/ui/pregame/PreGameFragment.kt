package com.aldidwiki.myquizapp.presentation.ui.pregame

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItems
import com.aldidwiki.myquizapp.R
import com.aldidwiki.myquizapp.data.model.QuestionParameter
import com.aldidwiki.myquizapp.data.source.remote.network.ApiResponse
import com.aldidwiki.myquizapp.databinding.FragmentPreGameBinding
import com.aldidwiki.myquizapp.helper.Handlers
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PreGameFragment : Fragment(), Handlers {
    private var _binding: FragmentPreGameBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<PreGameViewModel>()
    private val args by navArgs<PreGameFragmentArgs>()
    private var difficulties: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_pre_game, container, false)
        binding.handlers = this
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as AppCompatActivity
        with(activity.supportActionBar) {
            this?.setDisplayHomeAsUpEnabled(true)
            this?.title = args.categoryItem.categoryName
        }

        binding.edtName.requestFocus()
        setupOptionMenu()
    }

    private fun setupOptionMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == android.R.id.home) {
                    findNavController().navigateUp()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onResume() {
        super.onResume()
        difficulties?.let {
            binding.btnChangeDifficulties.text = resources.getString(R.string.difficulties_text, it)
        }
    }

    private fun TextInputEditText.check(): Boolean {
        return if (this.text.isNullOrBlank()) {
            binding.edtNameLayout.error = resources.getString(R.string.error_message_edt)
            this.addTextChangedListener {
                if (!it.isNullOrBlank()) binding.edtNameLayout.error = null
            }
            false
        } else {
            hideKeyboard()
            true
        }
    }

    private fun subscribeData() {
        with(binding) {
            viewModel.getToken().observe(viewLifecycleOwner) { state ->
                when (state) {
                    is ApiResponse.Success -> {
                        val questionParams = QuestionParameter(
                                token = state.body?.token!!,
                                categoryId = args.categoryItem.categoryId,
                                difficulty = difficulties?.lowercase(Locale.ROOT)
                        )

                        val toGame = PreGameFragmentDirections.actionPreGameFragmentToGameFragment(
                                userName = edtName.text.toString().trimStart(),
                                questionParams = questionParams
                        )
                        findNavController().navigate(toGame)
                    }
                    is ApiResponse.Loading -> btnStart.startAnimation()
                    is ApiResponse.Error -> {
                        btnStart.revertAnimation()
                        Toast.makeText(activity, resources.getString(R.string.error_message), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val view = activity?.currentFocus
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onBtnDifficultiesClick(view: View) {
        val listMenu = resources.getStringArray(R.array.list_difficulties).toList()
        MaterialDialog(requireContext()).show {
            title(text = resources.getString(R.string.change))
            listItems(items = listMenu) { _, _, text ->
                binding.btnChangeDifficulties.text = resources.getString(R.string.difficulties_text, text)
                difficulties = text.toString()
            }
            lifecycleOwner(viewLifecycleOwner)
        }
    }

    override fun onBtnStartClick(view: View) {
        if (binding.edtName.check()) {
            viewModel.clearQuestionEntity()
            subscribeData()
        }
    }
}