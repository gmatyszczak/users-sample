package pl.gmat.users.feature.list

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.gmat.users.common.model.User

@BindingAdapter("usersList")
fun usersList(recyclerView: RecyclerView, users: List<User>?) {
    (recyclerView.adapter as? UsersListAdapter)?.submitList(users)
}