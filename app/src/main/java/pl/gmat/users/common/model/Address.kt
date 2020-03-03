package pl.gmat.users.common.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    val id: Long = 0,
    val value: String = ""
) : Parcelable