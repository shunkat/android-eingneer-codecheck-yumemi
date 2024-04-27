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
    constructor(private val repository: GithubRepository) : ViewModel() {
        private val _searchResult = MutableLiveData<List<RepositoryInfo>>()
        val searchResult: LiveData<List<RepositoryInfo>> get() = _searchResult

        private val _errorMessage = MutableLiveData<String>()
        val errorMessage: LiveData<String> get() = _errorMessage

        fun searchResults(inputText: String) {
            viewModelScope.launch {
                try {
                    _searchResult.value = repository.searchRepositories(inputText)
                } catch (e: Exception) {
                    _errorMessage.postValue("エラーが発生しました: ${e.message}")
                }
            }
        }
    }
