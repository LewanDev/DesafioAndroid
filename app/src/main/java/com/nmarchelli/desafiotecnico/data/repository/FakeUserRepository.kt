package com.nmarchelli.desafiotecnico.data.repository

import com.nmarchelli.desafiotecnico.data.model.Dob
import com.nmarchelli.desafiotecnico.data.model.Location
import com.nmarchelli.desafiotecnico.data.model.Name
import com.nmarchelli.desafiotecnico.data.model.Picture
import com.nmarchelli.desafiotecnico.data.model.Street
import com.nmarchelli.desafiotecnico.data.model.UserModel
import kotlinx.coroutines.delay

class FakeUserRepository(private val seed: String = "challenge") {

    private val fixedUsers = listOf(
        UserModel(
            name = Name("JohnDoeDev","John", "Doe"),
            location = Location(
                street = Street(1,"123 Main St"),
                city = "City",
                state = "State",
                country = "Country",
            ),
            email = "john.doe@example.com",
            dob = Dob("1990-01-01T00:00:00Z", 33),
            phone = "123456789",
            picture = Picture(
                large = "",
                medium = "",
                thumbnail = ""
            ),
            nat = "US"
        ),
        UserModel(
            name = Name("JaneSmithDev","Jane", "Smith"),
            location = Location(
                street = Street(2, "456 Elm St"),
                city = "City2",
                state = "State2",
                country = "Country2",
            ),
            email = "jane.smith@example.com",
            dob = Dob("1995-05-05T00:00:00Z", 28),
            phone = "987654321",
            picture = Picture(
                large = "",
                medium = "",
                thumbnail = ""
            ),
            nat = "CA"
        )
    )

    suspend fun getUsers(): List<UserModel> {
        delay(50)
        return fixedUsers
    }
}
