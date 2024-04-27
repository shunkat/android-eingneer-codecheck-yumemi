package jp.co.yumemi.android.code_check.data.repository

import android.content.Context
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.model.Item
import org.json.JSONObject

class GithubRepository(private val context: Context) {
    private val client = HttpClient(Android)

    suspend fun searchRepositories(query: String): List<Item> {
        val response: HttpResponse =
            client.get("https://api.github.com/search/repositories") {
                header("Accept", "application/vnd.github.v3+json")
                parameter("q", query)
            }

        val jsonBody = JSONObject(response.receive<String>())
        val jsonItems = jsonBody.optJSONArray("items")!!
        val items = mutableListOf<Item>()

        for (i in 0 until jsonItems.length()) {
            val jsonItem = jsonItems.getJSONObject(i)
            val name = jsonItem.optString("full_name")
            val ownerIconUrl = jsonItem.getJSONObject("owner").optString("avatar_url")
            val language = jsonItem.optString("language")
            val stargazersCount = jsonItem.optLong("stargazers_count")
            val watchersCount = jsonItem.optLong("watchers_count")
            val forksCount = jsonItem.optLong("forks_count")
            val openIssuesCount = jsonItem.optLong("open_issues_count")

            items.add(
                Item(
                    name = name,
                    ownerIconUrl = ownerIconUrl,
                    language = context.getString(R.string.written_language, language),
                    stargazersCount = stargazersCount,
                    watchersCount = watchersCount,
                    forksCount = forksCount,
                    openIssuesCount = openIssuesCount,
                ),
            )
        }
        return items
    }
}
