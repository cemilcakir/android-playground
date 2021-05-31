package com.ccakir.feature_repository.details.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ccakir.feature_repository.databinding.ItemCommitBinding
import com.ccakir.feature_repository.details.domain.CommitDomainModel

class CommitAdapter :
    ListAdapter<CommitDomainModel, CommitAdapter.ViewHolder>(
        CommitDomainModelDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemCommitBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CommitDomainModel) {
            binding.txtMessage.text = item.message
            binding.txtCommitterAndDate.text = "by ${item.committer} at ${item.date}"
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCommitBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    internal class CommitDomainModelDiffCallback :
        DiffUtil.ItemCallback<CommitDomainModel>() {
        override fun areItemsTheSame(
            oldItem: CommitDomainModel,
            newItem: CommitDomainModel
        ): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(
            oldItem: CommitDomainModel,
            newItem: CommitDomainModel
        ): Boolean {
            return oldItem == newItem
        }

    }
}