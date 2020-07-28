package com.example.retrofitroomsample.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.*
import com.example.retrofitroomsample.model.Item
import com.example.retrofitroomsample.model.User
import com.example.retrofitroomsample.paging.ItemPagingSource
import com.example.retrofitroomsample.repo.AuthRepository
import com.example.retrofitroomsample.repo.NetworkRepository
import com.example.retrofitroomsample.repo.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataViewModel @ViewModelInject constructor(
    private val authRepository: AuthRepository,
    networkRepository: NetworkRepository,
    private val userRepository: UserRepository
) : ViewModel(), LifecycleObserver {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _item = MutableLiveData<Item>()
    val item: LiveData<Item> = _item

    fun setItem(item: Item) {
        _item.value = item
    }

    val usersFlow = Pager(PagingConfig(pageSize = 1, initialLoadSize = 1)) {
        ItemPagingSource(networkRepository)
    }.flow.cachedIn(viewModelScope)

    fun login(email: String, password: String) =
        liveData(Dispatchers.IO) {
            val result = userRepository.login(email, password)
            if (result) {
                _user.postValue(userRepository.getUser(email))
                authRepository.login(email)
            }
            emit(result)
        }

    fun register(user: User) =
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.register(user)
            authRepository.login(user.email)
            _user.postValue(user)
        }

    fun checkEmail(email: String) =
        liveData(Dispatchers.IO) {
            emit(userRepository.check(email))
        }

    fun logout() =
        viewModelScope.launch(Dispatchers.IO) {
            _user.postValue(null)
            authRepository.logout()
        }

    fun isLoggedIn(): Boolean {
        val isLoggedIn = authRepository.isLoggedIn()
        val email = authRepository.email()
        viewModelScope.launch(Dispatchers.IO) {
            if (isLoggedIn && email != null) _user.postValue(userRepository.getUser(email))
        }
        return isLoggedIn
    }

}