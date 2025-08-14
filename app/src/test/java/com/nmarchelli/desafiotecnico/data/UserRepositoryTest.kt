package com.nmarchelli.desafiotecnico.data

import com.nmarchelli.desafiotecnico.data.repository.FakeUserRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class UserRepositoryTest {

    @Test
    fun loadUsers_shouldReturnConsistentList_forSeedChallenge() {
        runTest {
            val repo = FakeUserRepository(seed = "challenge")

            val firstLoad = repo.getUsers()
            val secondLoad = repo.getUsers()

            assertEquals(firstLoad, secondLoad)
        }
    }
}
