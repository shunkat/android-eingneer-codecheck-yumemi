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

    // viewの初期化
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

        binding.searchInputText.setOnEditorActionListener { editText, action, _ ->
            // 検索アクションかつテキストが何か入力されている時だけ、検索処理を呼び出す
            if (action == EditorInfo.IME_ACTION_SEARCH && editText.text.isNotEmpty()) {
                viewModel.searchResults(editText.text.toString())
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun setupObservers() {
        // 検索結果が帰ってきたらリスト更新
        viewModel.searchResult.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun gotoRepositoryFragment(repositoryInfo: RepositoryInfo) {
        val action = SearchFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(repositoryInfo = repositoryInfo)
        findNavController().navigate(action)
    }
}
