package com.ccakir.androidplayground.features.repository.list.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ccakir.androidplayground.databinding.ItemRepositoryBinding
import com.ccakir.androidplayground.features.repository.list.domain.RepositoryDomainModel

class RepositoryListAdapter(private val onRepositoryClick: (repository: RepositoryDomainModel) -> Unit) :
    ListAdapter<RepositoryDomainModel, RepositoryListAdapter.ViewHolder>(
        RepositoryDomainModelDiffCallback()
    ) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onRepositoryClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: RepositoryDomainModel,
            onRepositoryClick: (repository: RepositoryDomainModel) -> Unit
        ) {
            binding.txtName.text = item.name
            binding.txtDescription.text = item.description
            binding.imgLanguage.setImageResource(item.languageIcon)
            binding.root.setOnClickListener {
                onRepositoryClick(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRepositoryBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    internal class RepositoryDomainModelDiffCallback :
        DiffUtil.ItemCallback<RepositoryDomainModel>() {
        override fun areItemsTheSame(
            oldItem: RepositoryDomainModel,
            newItem: RepositoryDomainModel
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: RepositoryDomainModel,
            newItem: RepositoryDomainModel
        ): Boolean {
            return oldItem == newItem
        }

    }
}