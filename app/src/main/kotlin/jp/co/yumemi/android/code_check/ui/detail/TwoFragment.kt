package jp.co.yumemi.android.code_check.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentTwoBinding
import jp.co.yumemi.android.code_check.ui.search.TimeManager.Companion.lastSearchDate

class TwoFragment : Fragment(R.layout.fragment_two) {
    private val args: TwoFragmentArgs by navArgs()
    private var binding: FragmentTwoBinding? = null
    private val safeBinding get() =
        binding ?: throw IllegalStateException(
            "Binding is accessed before onCreateView or after onDestroyView",
        )

    // リソース文字列のロード
    private lateinit var starsLabel: String
    private lateinit var watchersLabel: String
    private lateinit var forksLabel: String
    private lateinit var issuesLabel: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTwoBinding.inflate(inflater, container, false)

        // リソースの初期化
        starsLabel = getString(R.string.stars_count)
        watchersLabel = getString(R.string.watchers_count)
        forksLabel = getString(R.string.forks_count)
        issuesLabel = getString(R.string.open_issues_count)

        return safeBinding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: そもそも必要かどうか検討
        // TODO: 必要ならデバッグビルドの時だけ呼び出すような形に変更
        Log.d("Last Search Date", lastSearchDate.toString())

        val item = args.item

        with(safeBinding) {
            ownerIconView.load(item.ownerIconUrl)
            nameView.text = item.name
            languageView.text = item.language
            starsView.text = String.format(starsLabel, item.stargazersCount)
            watchersView.text = String.format(watchersLabel, item.watchersCount)
            forksView.text = String.format(forksLabel, item.forksCount)
            openIssuesView.text = String.format(issuesLabel, item.openIssuesCount)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
