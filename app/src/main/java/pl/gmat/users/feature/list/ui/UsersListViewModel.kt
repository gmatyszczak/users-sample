package pl.gmat.users.feature.list.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.gmat.users.common.model.User
import pl.gmat.users.common.ui.SingleLiveEvent
import pl.gmat.users.feature.list.data.UsersListRepository

class UsersListViewModel @ViewModelInject constructor(
    private val usersListRepository: UsersListRepository
) : ViewModel() {

    val state = MutableLiveData<UsersListState>().apply { value =
        UsersListState()
    }
    val effect = SingleLiveEvent<UsersListEffect>()

    private val currentState get() = state.value ?: UsersListState()

    init {
        viewModelScope.launch {
            usersListRepository.loadUsers().collect {
                state.value = currentState.copy(users = it)
            }
        }
    }

    fun onAddClicked() {
        effect.value = UsersListEffect.ShowAddUser
    }

    fun onDeleteAllClicked() = viewModelScope.launch {
        usersListRepository.deleteUsers()
    }

    fun onUserClicked(user: User) {
        effect.value = UsersListEffect.ShowUserDetails(user.id)
    }
}