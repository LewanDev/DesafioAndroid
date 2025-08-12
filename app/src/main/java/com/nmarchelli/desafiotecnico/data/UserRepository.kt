package com.nmarchelli.desafiotecnico.data

import com.nmarchelli.desafiotecnico.data.model.UserModel
import com.nmarchelli.desafiotecnico.data.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getTenUsers(page: Int): Response<UserModel> {
        return apiService.getTenUsers(page)
    }
}