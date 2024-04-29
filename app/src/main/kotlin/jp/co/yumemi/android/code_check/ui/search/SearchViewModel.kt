/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.search
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.code_check.data.model.RepositoryInfo
import jp.co.yumemi.android.code_check.data.repository.GithubRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel
    @Inject
    constructor(
        private val repository: GithubRepository,
    ) : ViewModel() {
        private val _searchResult = MutableLiveData<List<RepositoryInfo>>()
        val searchResult: LiveData<List<RepositoryInfo>> get() = _searchResult

        private val _errorMessage = MutableLiveData<String>()
        val errorMessage: LiveData<String> get() = _errorMessage

        private var currentPage = 1
        private var hasNextPage = true
        private var currentQuery = ""

        fun searchResults(
            inputText: String,
            page: Int = 1,
        ) {
            currentQuery = inputText
            viewModelScope.launch {
                try {
                    val results = repository.searchRepositories(inputText, page)
                    if (results.isEmpty() && page != 1) {
                        hasNextPage = false
                    } else {
                        _searchResult.postValue(results)
                        if (page == 1) {
                            preloadNextPage() // 最初のページのロード後に次のページをプリロード
                        }
                    }
                } catch (e: Exception) {
                    _errorMessage.postValue("エラーが発生しました: ${e.message}")
                }
            }
        }

        private fun preloadNextPage() {
            if (hasNextPage) {
                searchResults(currentQuery, currentPage + 1)
            }
        }

        fun loadNextPage() {
            if (hasNextPage) {
                currentPage++
                searchResults(currentQuery, currentPage)
            }
        }

        fun loadPreviousPage() {
            if (currentPage > 1) {
                currentPage--
                searchResults(currentQuery, currentPage)
            }
        }
    }
