package com.nmarchelli.desafiotecnico.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


data class UserResponse(
    val results: List<UserModel>,
    val info: Info
)

data class Info(
    val seed: String,
    val results: Int,
    val page: Int,
    val version: String
)
@Parcelize
data class UserModel(
    val name: Name,
    val location: Location,
    val email: String,
    val dob: Dob,
    val phone: String,
    val picture: Picture,
    val nat: String
): Parcelable

@Parcelize
data class Name(
    val title: String,
    val first: String,
    val last: String
): Parcelable {
    fun getFullName(): String {
        return "$title $first $last"
    }
}

@Parcelize
data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
): Parcelable {
    fun getFullAddress(): String {
        return "${street.number} ${street.name}, $city, $state, $country"
    }
}

@Parcelize
data class Street(
    val number: Int,
    val name: String
): Parcelable

@Parcelize
data class Dob(
    val date: String,
    val age: Int
): Parcelable

@Parcelize
data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
): Parcelable
