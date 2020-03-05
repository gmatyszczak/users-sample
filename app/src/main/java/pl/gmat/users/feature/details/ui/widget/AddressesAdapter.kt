package pl.gmat.users.feature.details.ui.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.gmat.users.common.model.Address
import pl.gmat.users.databinding.ItemAddressBinding

private val diffCallback = object : DiffUtil.ItemCallback<Address>() {
    override fun areItemsTheSame(oldItem: Address, newItem: Address) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Address, newItem: Address) = oldItem == newItem
}

class AddressesAdapter(
    private val lifecycleOwner: LifecycleOwner
) : ListAdapter<Address, AddressViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAddressBinding.inflate(inflater, parent, false).apply {
            lifecycleOwner = this@AddressesAdapter.lifecycleOwner
        }
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class AddressViewHolder(private val binding: ItemAddressBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(address: Address?) = binding.run {
        if (address != null) {
            this.address = address
            executePendingBindings()
        }
    }
}
