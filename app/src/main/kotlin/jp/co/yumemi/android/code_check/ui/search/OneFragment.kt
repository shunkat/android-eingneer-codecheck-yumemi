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
import jp.co.yumemi.android.code_check.data.model.Item
import jp.co.yumemi.android.code_check.databinding.FragmentOneBinding
import javax.inject.Inject

@AndroidEntryPoint
class OneFragment : Fragment() {
    @Inject lateinit var viewModel: OneViewModel
    private var binding: FragmentOneBinding? = null
    private val safeBinding get() = binding!!

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var dividerItemDecoration: DividerItemDecoration
    private lateinit var adapter: CustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentOneBinding.inflate(inflater, container, false)
        return safeBinding.root
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
        layoutManager = LinearLayoutManager(requireContext())
        dividerItemDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        adapter =
            CustomAdapter(
                object : CustomAdapter.OnItemClickListener {
                    override fun itemClick(item: Item) {
                        gotoRepositoryFragment(item)
                    }
                },
            )

        safeBinding.recyclerView.apply {
            layoutManager = this@OneFragment.layoutManager
            addItemDecoration(dividerItemDecoration)
            adapter = this@OneFragment.adapter
        }

        safeBinding.searchInputText.setOnEditorActionListener { editText, action, _ ->
            if (action == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchResults(editText.text.toString())
                viewModel.updateSearchDate()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun setupObservers() {
        viewModel.searchResult.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun gotoRepositoryFragment(item: Item) {
        val action = OneFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item = item)
        findNavController().navigate(action)
    }
}
