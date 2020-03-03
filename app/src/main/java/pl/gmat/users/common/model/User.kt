package pl.gmat.users.common.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Long = 0,
    val firstName: String = "",
    val lastName: String = "",
    val age: String = "",
    val gender: Gender = Gender.MALE,
    val address: Address = Address()
): Parcelable