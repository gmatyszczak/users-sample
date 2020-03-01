package pl.gmat.users.feature.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.gmat.users.common.SingleLiveEvent
import javax.inject.Inject

class UsersListViewModel @Inject constructor(
    private val usersListRepository: UsersListRepository
) : ViewModel() {

    val state = MutableLiveData<UsersListState>().apply { value = UsersListState() }
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

    fun onDeleteAllClicked() = Unit
}