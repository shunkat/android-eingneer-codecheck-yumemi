package jp.co.yumemi.android.code_check.ui.search

import org.junit.Test
import org.junit.Assert.*
import jp.co.yumemi.android.code_check.data.model.RepositoryInfo


class TaskDiffCallbackTest {

    // 異なるアイテムが同じではないことをテスト
    @Test
    fun `areItemsTheSame with different items should return false`() {
        // 異なるリポジトリ情報を持つ二つのオブジェクトを作成
        val repo1 = RepositoryInfo("repo1", "", "", 0, 0, 0, 0)
        val repo2 = RepositoryInfo("repo2", "", "", 0, 0, 0, 0)
        val callback = TaskDiffCallback()

        // アイテムが異なるため、falseを返すことを期待
        assertFalse(callback.areItemsTheSame(repo1, repo2))
    }

    // 同一のアイテムが同じであることをテスト
    @Test
    fun `areItemsTheSame with same items should return true`() {
        // 同一のリポジトリ情報を持つオブジェクトを作成
        val repo = RepositoryInfo("repo", "", "", 0, 0, 0, 0)
        val callback = TaskDiffCallback()

        // 同一のオブジェクトを比較するため、trueを返すことを期待
        assertTrue(callback.areItemsTheSame(repo, repo))
    }

    // 異なる内容のアイテムが同一でないことをテスト
    @Test
    fun `areContentsTheSame with different content should return false`() {
        // 同一のリポジトリ名でも異なるプログラミング言語を持つ二つのオブジェクトを作成
        val repo1 = RepositoryInfo("repo", "", "Java", 10, 20, 5, 3)
        val repo2 = RepositoryInfo("repo", "", "Kotlin", 10, 20, 5, 3)
        val callback = TaskDiffCallback()

        // 内容が異なるため、falseを返すことを期待
        assertFalse(callback.areContentsTheSame(repo1, repo2))
    }

    // 同一内容のアイテムが同じであることをテスト
    @Test
    fun `areContentsTheSame with same content should return true`() {
        // 同一のリポジトリ情報を持つオブジェクトを作成
        val repo = RepositoryInfo("repo", "", "Java", 10, 20, 5, 3)
        val callback = TaskDiffCallback()

        // 全ての属性が同じため、trueを返すことを期待
        assertTrue(callback.areContentsTheSame(repo, repo))
    }
}
