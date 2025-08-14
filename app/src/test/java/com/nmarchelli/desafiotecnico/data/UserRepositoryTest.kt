package com.nmarchelli.desafiotecnico.data

import com.nmarchelli.desafiotecnico.data.repository.FakeUserRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class UserRepositoryTest {

    @Test
    fun loadUsers_shouldReturnConsistentList_forSeedChallenge() = runTest {
        val repo = FakeUserRepository(seed = "challenge")

        val firstLoad = repo.getUsers(page = 1, nat = emptyList())
        val secondLoad = repo.getUsers(page = 1, nat = emptyList())

        assertEquals(firstLoad.body(), secondLoad.body())
    }


    @Test
    fun loadUsers_shouldFilterByNat() = runTest {
        val repo = FakeUserRepository(seed = "challenge")

        val onlyUS = repo.getUsers(page = 1, nat = listOf("US"))
        val onlyCA = repo.getUsers(page = 1, nat = listOf("CA"))

        assertEquals("US", onlyUS.body()?.results?.first()?.nat)
        assertEquals("CA", onlyCA.body()?.results?.first()?.nat)
    }

    @Test
    fun loadUsers_shouldRespectPageParameter() = runTest {
        val repo = FakeUserRepository(seed = "challenge")

        val page1 = repo.getUsers(page = 1, nat = emptyList())
        val page2 = repo.getUsers(page = 2, nat = emptyList())

        assertEquals(1, page1.body()?.info?.page)
        assertEquals(2, page2.body()?.info?.page)
    }
}
