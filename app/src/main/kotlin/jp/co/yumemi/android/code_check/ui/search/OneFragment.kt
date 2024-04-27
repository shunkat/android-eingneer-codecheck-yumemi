package jp.co.yumemi.android.code_check.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.data.model.item
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding
import javax.inject.Inject

@AndroidEntryPoint
class OneFragment : Fragment() {
    @Inject lateinit var _viewModel: OneViewModel
    private var _binding: FragmentOneBinding? = null
    private val binding get() = _binding!!

    private lateinit var _layoutManager: LinearLayoutManager
    private lateinit var _dividerItemDecoration: DividerItemDecoration
    private lateinit var _adapter: CustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setupObservers()
    }

    private fun initViews() {
        _layoutManager = LinearLayoutManager(requireContext())
        _dividerItemDecoration = DividerItemDecoration(requireContext(), _layoutManager.orientation)
        _adapter =
            CustomAdapter(
                object : CustomAdapter.OnItemClickListener {
                    override fun itemClick(item: item) {
                        gotoRepositoryFragment(item)
                    }
                },
            )

        binding.recyclerView.apply {
            layoutManager = this@OneFragment._layoutManager
            addItemDecoration(_dividerItemDecoration)
            adapter = this@OneFragment._adapter
        }

        binding.searchInputText.setOnEditorActionListener { editText, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                _viewModel.searchResults(editText.text.toString())
                _viewModel.updateSearchDate()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun setupObservers() {
        _viewModel.searchResult.observe(viewLifecycleOwner) {
            _adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun gotoRepositoryFragment(item: item) {
        val _action = OneFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item = item)
        findNavController().navigate(_action)
    }
}
