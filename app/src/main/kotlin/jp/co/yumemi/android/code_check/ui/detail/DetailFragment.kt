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
import jp.co.yumemi.android.code_check.databinding.FragmentDetailBinding
import jp.co.yumemi.android.code_check.ui.search.TimeManager.Companion.lastSearchDate

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val args: DetailFragmentArgs by navArgs()
    private var binding: FragmentDetailBinding? = null
    private val safeBinding get() =
        binding ?: throw IllegalStateException(
            "Binding is accessed before onCreateView or after onDestroyView",
        )

    // リソース文字列のロード
    private lateinit var countUnitStars: String
    private lateinit var countUnitWatchers: String
    private lateinit var countUnitForks: String
    private lateinit var countUnitOpenIssues: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        // リソースの初期化
        countUnitStars = getString(R.string.count_unit_stars)
        countUnitWatchers = getString(R.string.count_unit_watchers)
        countUnitForks = getString(R.string.count_unit_forks)
        countUnitOpenIssues = getString(R.string.count_unit_open_issues)

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

        val repositoryInfo = args.repositoryInfo

        // repositoryの情報をviewに反映
        with(safeBinding) {
            ivOwnerIcon.load(repositoryInfo.ownerAvatarUrl)
            tvRepositoryAndOwnerName.text = repositoryInfo.repositoryAndOwnerName
            tvWrittenLanguage.text = repositoryInfo.language
            tvStarCount.text = String.format(countUnitStars, repositoryInfo.stargazersCount)
            tvWatcherCount.text = String.format(countUnitWatchers, repositoryInfo.watchersCount)
            tvForkCount.text = String.format(countUnitForks, repositoryInfo.forksCount)
            tvOpenIssueCount.text = String.format(countUnitOpenIssues, repositoryInfo.openIssuesCount)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
