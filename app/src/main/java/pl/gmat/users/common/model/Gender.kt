package pl.gmat.users.common.model

import androidx.annotation.StringRes
import pl.gmat.users.R

enum class Gender(@StringRes val nameResId: Int) {
    MALE(R.string.gender_male),
    FEMALE(R.string.gender_female),
    OTHER(R.string.gender_other)
}