package com.example.kasirtoko.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kasirtoko.repositories.SalesRepository
import com.example.kasirtoko.vo.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: SalesRepository
) : ViewModel() {

    private val _loginViewModel = MutableLiveData<State>()
    val loginViewModel: LiveData<State> get() = _loginViewModel

    fun login(email: String, password: String) {
        _loginViewModel.postValue(State.Loading)
        viewModelScope.launch {
            when(val res = repository.login(email, password)) {
                is ResultWrapper.Success -> _loginViewModel.value = State.Success(res.message)
                is ResultWrapper.Error -> _loginViewModel.value = State.Failed(res.message)
            }
        }
    }

    sealed class State {
        data class Success(val message: String): State()
        data class Failed(val message: String): State()
        object Loading: State()
    }

}