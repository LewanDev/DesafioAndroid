package com.nmarchelli.desafiotecnico.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nmarchelli.desafiotecnico.data.UserRepository
import com.nmarchelli.desafiotecnico.data.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {
    private val _users = MutableStateFlow<List<UserModel>>(emptyList())
    val users: StateFlow<List<UserModel>> = _users

    private val _page = MutableStateFlow(1)
    val page: StateFlow<Int> = _page

    fun getUsers(page: Int){
        viewModelScope.launch {
            val response = repository.getTenUsers(page)
            if(response.isSuccessful){
                _users.value = response.body()?.results ?: emptyList()
                _page.value = page
            }
        }
    }

    fun goToNextPage(){
        getUsers(_page.value + 1)
    }

    fun goToPreviousPage(){
        if(_page.value>1){
            getUsers(_page.value - 1)
        }
    }
}