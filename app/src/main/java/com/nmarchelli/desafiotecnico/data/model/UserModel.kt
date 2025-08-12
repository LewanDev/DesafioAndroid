package com.nmarchelli.desafiotecnico.data.model


data class UserModel(
    val results: List<User>
)

data class User(
    val name: Name,
    val location: Location,
    val email: String,
    val dateOfBirth: DateOfBirth,
    val phone: String,
    val picture: Picture,
    val nat: String
)

data class Name(
    val title: String,
    val first: String,
    val last: String
) {
    fun getFullName(): String {
        return "$title $first $last"
    }
}

data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: Any
) {
    fun getFullAddress(): String {
        return "${street.number} ${street.name}, $city, $state, $country"
    }
}

data class Street(
    val number: Int,
    val name: String
)

data class DateOfBirth(
    val date: String,
    val age: Int
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)
