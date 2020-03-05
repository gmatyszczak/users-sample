package pl.gmat.users.feature.edit.ui.widget

import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter

@BindingAdapter("testResId")
fun setTextFromResId(textView: TextView, @StringRes textResId: Int) {
    if (textResId != 0) {
        textView.setText(textResId)
    }
}