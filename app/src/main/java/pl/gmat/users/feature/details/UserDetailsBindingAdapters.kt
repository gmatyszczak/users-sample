package pl.gmat.users.feature.details

import android.widget.TextView
import androidx.databinding.BindingAdapter
import pl.gmat.users.common.model.Gender

@BindingAdapter("genderText")
fun genderText(textView: TextView, gender: Gender?) {
    gender?.let {
        textView.text = textView.context.getString(it.nameResId)
    }
}