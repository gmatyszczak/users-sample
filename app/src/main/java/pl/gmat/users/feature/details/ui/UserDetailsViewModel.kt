package pl.gmat.users.feature.details.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.gmat.users.common.ui.SingleLiveEvent
import pl.gmat.users.feature.details.data.UserDetailsRepository

class UserDetailsViewModel @ViewModelInject constructor(
    @Assisted private val savedState: SavedStateHandle,
    private val userDetailsRepository: UserDetailsRepository
) : ViewModel() {

    val state = MutableLiveData<UserDetailsState>().apply { value = UserDetailsState() }
    val effect = SingleLiveEvent<UserDetailsEffect>()

    private val currentState get() = state.value ?: UserDetailsState()
    private val userId = savedState.get<Long>(UserDetailsActivity.EXTRA_USER_ID) ?: -1

    init {
        viewModelScope.launch {
            userDetailsRepository.loadUser(userId).collect {
                it?.let { state.value = currentState.copy(user = it) }
            }
        }
    }

    fun onDeleteClicked() = viewModelScope.launch {
        userDetailsRepository.deleteUser(userId)
        effect.value = UserDetailsEffect.Finish
    }

    fun onEditClicked() {
        effect.value = UserDetailsEffect.ShowEditUser(currentState.user)
    }
}