package jp.co.yumemi.android.code_check.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.model.RepositoryInfo

class TaskDiffCallback : DiffUtil.ItemCallback<RepositoryInfo>() {
    override fun areItemsTheSame(
        oldRepositoryInfo: RepositoryInfo,
        newRepositoryInfo: RepositoryInfo,
    ): Boolean {
        return oldRepositoryInfo.repositoryAndOwnerName == newRepositoryInfo.repositoryAndOwnerName
    }

    override fun areContentsTheSame(
        oldRepositoryInfo: RepositoryInfo,
        newRepositoryInfo: RepositoryInfo,
    ): Boolean {
        return oldRepositoryInfo == newRepositoryInfo
    }
}

class RepositoryAdapter(private val itemClickListener: OnItemClickListener) : ListAdapter<RepositoryInfo, RepositoryAdapter.ViewHolder>(
    TaskDiffCallback(),
) {
    // viewHolderにTextViewをキャッシュしておくことで毎回findViewByIdを呼ばないようにする
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val repositoryNameTextView: TextView = view.findViewById(R.id.tvRepositoryName)
    }

    interface OnItemClickListener {
        fun onRepositoryClick(repositoryInfo: RepositoryInfo)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repository_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val repository = getItem(position)
        holder.repositoryNameTextView.text = repository.repositoryAndOwnerName
        holder.itemView.setOnClickListener {
            itemClickListener.onRepositoryClick(repository)
        }
    }
}
