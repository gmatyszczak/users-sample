package pl.gmat.users.feature.details.ui.widget

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.gmat.users.common.model.Address
import pl.gmat.users.common.model.Gender

@BindingAdapter("genderText")
fun genderText(textView: TextView, gender: Gender?) {
    gender?.let {
        textView.text = textView.context.getString(it.nameResId)
    }
}

@BindingAdapter("addressesList")
fun setAddressesList(recyclerView: RecyclerView, addresses: List<Address>?) {
    if (addresses != null) {
        (recyclerView.adapter as? AddressesAdapter)?.submitList(addresses)
    }
}