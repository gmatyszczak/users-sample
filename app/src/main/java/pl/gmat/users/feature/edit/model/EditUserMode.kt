package pl.gmat.users.feature.edit.model

import androidx.annotation.StringRes
import pl.gmat.users.R
import pl.gmat.users.common.model.User

sealed class EditUserMode(@StringRes val submitButtonResId: Int) {
    data class Update(val user: User) : EditUserMode(R.string.edit)
    object Add : EditUserMode(R.string.add)
}