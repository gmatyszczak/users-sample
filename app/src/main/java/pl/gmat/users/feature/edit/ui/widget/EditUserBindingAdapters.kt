package pl.gmat.users.feature.edit.ui.widget

import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.gmat.users.common.model.Address

@BindingAdapter("testResId")
fun setTextFromResId(textView: TextView, @StringRes textResId: Int) {
    if (textResId != 0) {
        textView.setText(textResId)
    }
}

@BindingAdapter("addresses")
fun setAddresses(recyclerView: RecyclerView, addresses: List<Address>?) {
    if (addresses != null) {
        (recyclerView.adapter as? EditAddressesAdapter)?.submitList(addresses)
    }
}