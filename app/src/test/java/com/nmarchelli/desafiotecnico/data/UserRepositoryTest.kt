package com.nmarchelli.desafiotecnico.data

import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class UserRepositoryTest {

    @Test
    fun loadUsers_shouldReturnConsistentList_forSeedChallenge() {
        runTest {
            // Given
            val repo = FakeUserRepository(seed = "challenge")

            // When
            val firstLoad = repo.getUsers()
            val secondLoad = repo.getUsers()

            // Then
            assertEquals(firstLoad, secondLoad)
        }
    }
}
