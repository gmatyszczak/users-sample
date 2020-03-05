package pl.gmat.users.feature.list.ui.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.gmat.users.common.model.User
import pl.gmat.users.databinding.ItemUserBinding
import pl.gmat.users.feature.list.ui.UsersListViewModel

private val diffCallback = object : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
}

class UsersListAdapter(
    private val viewModel: UsersListViewModel,
    private val lifecycleOwner: LifecycleOwner
) : ListAdapter<User, UserViewHolder>(
    diffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false).apply {
            viewModel = this@UsersListAdapter.viewModel
            lifecycleOwner = this@UsersListAdapter.lifecycleOwner
        }
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User?) = binding.run {
        if (user != null) {
            this.user = user
            executePendingBindings()
        }
    }
}
