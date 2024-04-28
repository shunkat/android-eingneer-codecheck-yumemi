package jp.co.yumemi.android.code_check.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepositoryInfo(
    val repositoryAndOwnerName: String,
    val ownerAvatarUrl: String,
    val language: String?,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable
