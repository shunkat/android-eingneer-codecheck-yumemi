package jp.co.yumemi.android.code_check.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val args: DetailFragmentArgs by navArgs()
    private var binding: FragmentDetailBinding? = null
    private val safeBinding get() =
        binding ?: throw IllegalStateException(
            "Binding is accessed before onCreateView or after onDestroyView",
        )

    // リソース文字列のロード
    private val countUnitStars by lazy { getString(R.string.count_unit_stars) }
    private val countUnitWatchers by lazy { getString(R.string.count_unit_watchers) }
    private val countUnitForks by lazy { getString(R.string.count_unit_forks) }
    private val countUnitOpenIssues by lazy { getString(R.string.count_unit_open_issues) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return safeBinding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

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
