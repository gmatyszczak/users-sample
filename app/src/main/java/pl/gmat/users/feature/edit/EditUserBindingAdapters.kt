package pl.gmat.users.feature.edit

import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import pl.gmat.users.common.model.Address

@BindingAdapter("testResId")
fun setTextFromResId(textView: TextView, @StringRes textResId: Int) {
    if (textResId != 0) {
        textView.setText(textResId)
    }
}

@BindingAdapter("addresses")
fun setAddresses(spinner: Spinner, addresses: List<Address>?) {
    if (addresses != null) {
        (spinner.adapter as? AddressAdapter)?.submit(addresses.toTypedArray())
    }
}