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
import java.util.Date
import javax.inject.Inject

/**
 * TwoFragment で使う
 */
class TimeManager {
    companion object {
        var lastSearchDate: Date? = null

        fun updateSearchDate() {
            lastSearchDate = Date()
        }
    }
}

class SearchViewModel
    @Inject
    constructor(private val repository: GithubRepository) : ViewModel() {
        private val _searchResult = MutableLiveData<List<RepositoryInfo>>()
        val searchResult: LiveData<List<RepositoryInfo>> get() = _searchResult

        fun searchResults(inputText: String) {
            viewModelScope.launch {
                _searchResult.value = repository.searchRepositories(inputText)
            }
        }

        fun updateSearchDate() {
            TimeManager.updateSearchDate()
        }
    }