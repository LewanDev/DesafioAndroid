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

    private val _selectedNationalities = MutableStateFlow<List<String>>(emptyList())
    val selectedNationalities: StateFlow<List<String>> = _selectedNationalities

    private val _favorites = MutableStateFlow<Set<String>>(emptySet())
    val favorites: StateFlow<Set<String>> = _favorites

    fun setUsers(list: List<UserModel>) {
        _users.value = list
    }

    fun toggleFavorite(user: UserModel) {
        _favorites.value = if (_favorites.value.contains(user.email)) {
            _favorites.value - user.email
        } else {
            _favorites.value + user.email
        }
    }

    fun isFavorite(user: UserModel): Boolean {
        return _favorites.value.contains(user.email)
    }

    fun toggleNationality(nat: String) {
        val current = _selectedNationalities.value.toMutableList()
        if (current.contains(nat)) {
            current.remove(nat)
        } else {
            current.add(nat)
        }
        _selectedNationalities.value = current
        getUsers(1)
    }

    fun getUsers(page: Int){
        viewModelScope.launch {
            val response = repository.getTenUsers(page, _selectedNationalities.value)
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