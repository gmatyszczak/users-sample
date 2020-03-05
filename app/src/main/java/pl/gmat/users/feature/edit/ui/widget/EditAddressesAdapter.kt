package pl.gmat.users.feature.edit.ui.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.gmat.users.common.model.Address
import pl.gmat.users.databinding.ItemEditAddressBinding
import pl.gmat.users.feature.edit.ui.EditUserViewModel

private val diffCallback = object : DiffUtil.ItemCallback<Address>() {
    override fun areItemsTheSame(oldItem: Address, newItem: Address) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Address, newItem: Address) = oldItem == newItem
}

class EditAddressesAdapter(
    private val viewModel: EditUserViewModel,
    private val lifecycleOwner: LifecycleOwner
) : ListAdapter<Address, EditAddressViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditAddressViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEditAddressBinding.inflate(inflater, parent, false).apply {
            viewModel = this@EditAddressesAdapter.viewModel
            lifecycleOwner = this@EditAddressesAdapter.lifecycleOwner
        }
        return EditAddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EditAddressViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class EditAddressViewHolder(private val binding: ItemEditAddressBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(address: Address?) = binding.run {
        if (address != null) {
            this.address = address
            executePendingBindings()
        }
    }
}
