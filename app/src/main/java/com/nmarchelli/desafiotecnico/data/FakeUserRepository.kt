package com.nmarchelli.desafiotecnico.data

import com.nmarchelli.desafiotecnico.data.model.UserModel
import com.nmarchelli.desafiotecnico.data.model.UserResponse
import kotlinx.coroutines.delay
import retrofit2.Response

class FakeUserRepository(private val seed: String = "challenge") {

    private val fixedUsers = listOf(
        UserModel(
            name = com.nmarchelli.desafiotecnico.data.model.Name("JohnDoeDev","John", "Doe"),
            location = com.nmarchelli.desafiotecnico.data.model.Location(
                street = com.nmarchelli.desafiotecnico.data.model.Street(1,"123 Main St"),
                city = "City",
                state = "State",
                country = "Country",
            ),
            email = "john.doe@example.com",
            dob = com.nmarchelli.desafiotecnico.data.model.Dob("1990-01-01T00:00:00Z", 33),
            phone = "123456789",
            picture = com.nmarchelli.desafiotecnico.data.model.Picture(
                large = "",
                medium = "",
                thumbnail = ""
            ),
            nat = "US"
        ),
        UserModel(
            name = com.nmarchelli.desafiotecnico.data.model.Name("JaneSmithDev","Jane", "Smith"),
            location = com.nmarchelli.desafiotecnico.data.model.Location(
                street = com.nmarchelli.desafiotecnico.data.model.Street(2, "456 Elm St"),
                city = "City2",
                state = "State2",
                country = "Country2",
            ),
            email = "jane.smith@example.com",
            dob = com.nmarchelli.desafiotecnico.data.model.Dob("1995-05-05T00:00:00Z", 28),
            phone = "987654321",
            picture = com.nmarchelli.desafiotecnico.data.model.Picture(
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
