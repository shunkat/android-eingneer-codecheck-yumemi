package jp.co.yumemi.android.code_check.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.data.model.RepositoryInfo
import jp.co.yumemi.android.code_check.databinding.FragmentSearchBinding
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    @Inject lateinit var viewModel: SearchViewModel
    private var binding: FragmentSearchBinding? = null
    private val safeBinding get() = binding!!

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var dividerItemDecoration: DividerItemDecoration
    private lateinit var adapter: RepositoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return safeBinding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            initViews(it)
            setupObservers()
        }
    }

    private fun initViews(binding: FragmentSearchBinding) {
        layoutManager = LinearLayoutManager(requireContext())
        dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        adapter =
            RepositoryAdapter(
                object : RepositoryAdapter.OnItemClickListener {
                    override fun onRepositoryClick(repositoryInfo: RepositoryInfo) {
                        gotoRepositoryFragment(repositoryInfo)
                    }
                },
            )

        binding.rvRepositoryList.apply {
            layoutManager = this@SearchFragment.layoutManager
            addItemDecoration(dividerItemDecoration)
            adapter = this@SearchFragment.adapter
        }

        binding.searchInputText.setOnEditorActionListener { editText, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH && editText.text.isNotEmpty()) {
                viewModel.searchResults(editText.text.toString())
                hideKeyboard()
                showLoadingIndicator(true)
                return@setOnEditorActionListener true
            }
            false
        }

        binding.nextPageButton.setOnClickListener {
            viewModel.loadNextPage()
        }

        binding.prevPageButton.setOnClickListener {
            viewModel.loadPreviousPage()
        }
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun showLoadingIndicator(show: Boolean) {
        binding?.progressBar?.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun setupObservers() {
        viewModel.searchResult.observe(viewLifecycleOwner) { results ->
            showLoadingIndicator(false)
            if (results.isEmpty()) {
                Toast.makeText(requireContext(), "検索結果が0件でした。", Toast.LENGTH_LONG).show()
            } else {
                adapter.submitList(results)
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            showLoadingIndicator(false)
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun gotoRepositoryFragment(repositoryInfo: RepositoryInfo) {
        // 遷移処理の名前がおかしいことに気づいてしまった
        val action = SearchFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(repositoryInfo)
        findNavController().navigate(action)
    }
}
