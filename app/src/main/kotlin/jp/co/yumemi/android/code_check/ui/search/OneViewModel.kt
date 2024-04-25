/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.data.model.item
import jp.co.yumemi.android.code_check.data.repository.GithubRepository
import kotlinx.coroutines.launch
import java.util.Date

/**
 * TwoFragment で使う
 */
class OneViewModel(private val repository: GithubRepository) : ViewModel() {
    fun searchResults(inputText: String): LiveData<List<item>> {
        val result = MutableLiveData<List<item>>()
        viewModelScope.launch {
            result.value = repository.searchRepositories(inputText)
            lastSearchDate = Date() // 検索日時を更新
        }
        return result
    }
}
