package jp.co.yumemi.android.code_check.data.repository

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import jp.co.yumemi.android.code_check.data.model.RepositoryInfo
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.json.JSONObject

class GithubRepository(private val context: Context) {
    private val client = HttpClient(Android)

    suspend fun fetchAllRepositories(query: String): List<RepositoryInfo> =
        coroutineScope {
            val pages = listOf(1, 2, 3) // 例えば最初の3ページを取得
            pages.map { page ->
                async {
                    searchRepositories(query, page)
                }
            }.awaitAll().flatten() // すべてのページからの結果を平坦化して結合
        }

    suspend fun searchRepositories(
        query: String,
        page: Int,
    ): List<RepositoryInfo> {
        val response: HttpResponse =
            client.get("https://api.github.com/search/repositories") {
                header("Accept", "application/vnd.github.v3+json")
                parameter("q", query)
                parameter("page", page)
                parameter("per_page", 100) // ページあたりのアイテム数を100に設定
            }

        val json = JSONObject(response.receive<String>())
        val items =
            json.optJSONArray("items")
                ?: return emptyList() // リストが空の場合は空のリストを返す。

        return List(items.length()) { i ->
            items.optJSONObject(i)?.let { jsonItem ->
                val repositoryAndOwnerName = jsonItem.optString("full_name")
                val ownerAvatarUrl = jsonItem.getJSONObject("owner").optString("avatar_url")
                val language = jsonItem.opt("language") as? String
                val stargazersCount = jsonItem.optLong("stargazers_count")
                val watchersCount = jsonItem.optLong("watchers_count")
                val forksCount = jsonItem.optLong("forks_count")
                val openIssuesCount = jsonItem.optLong("open_issues_count")

                RepositoryInfo(
                    repositoryAndOwnerName = repositoryAndOwnerName,
                    ownerAvatarUrl = ownerAvatarUrl,
                    language = language,
                    stargazersCount = stargazersCount,
                    watchersCount = watchersCount,
                    forksCount = forksCount,
                    openIssuesCount = openIssuesCount,
                )
            }
        }.filterNotNull() // null itemsを除外
    }
}
