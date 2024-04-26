package jp.co.yumemi.android.code_check.ui.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.model.item
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding
import javax.inject.Inject

@AndroidEntryPoint
class OneFragment : Fragment(R.layout.fragment_one) {
    @Inject lateinit var _viewModel: OneViewModel

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val _binding = FragmentOneBinding.bind(view)
        val _layoutManager = LinearLayoutManager(requireContext())
        val _dividerItemDecoration = DividerItemDecoration(requireContext(), _layoutManager.orientation)
        val _adapter =
            CustomAdapter(
                object : CustomAdapter.OnItemClickListener {
                    override fun itemClick(item: item) {
                        gotoRepositoryFragment(item)
                    }
                },
            )

        _binding.searchInputText.setOnEditorActionListener { editText, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                _viewModel.searchResults(editText.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }

        _viewModel.searchResult.observe(viewLifecycleOwner) {
            _adapter.submitList(it)
        }

        _binding.recyclerView.apply {
            layoutManager = _layoutManager
            addItemDecoration(_dividerItemDecoration)
            adapter = _adapter
        }
    }

    private fun gotoRepositoryFragment(item: item) {
        val _action = OneFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item = item)
        findNavController().navigate(_action)
    }
}
