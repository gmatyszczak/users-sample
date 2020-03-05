package pl.gmat.users.feature.details.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pl.gmat.users.common.ui.SingleLiveEvent
import pl.gmat.users.feature.details.data.UserDetailsRepository
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(
    private val userDetailsRepository: UserDetailsRepository
) : ViewModel() {

    val state = MutableLiveData<UserDetailsState>().apply { value = UserDetailsState() }
    val effect = SingleLiveEvent<UserDetailsEffect>()

    private val currentState get() = state.value ?: UserDetailsState()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            userDetailsRepository.loadUser().collect {
                it?.let { state.value = currentState.copy(user = it) }
            }
        }
    }

    fun onDeleteClicked() = viewModelScope.launch(Dispatchers.Main) {
        userDetailsRepository.deleteUser()
        effect.value = UserDetailsEffect.Finish
    }

    fun onEditClicked() {
        effect.value = UserDetailsEffect.ShowEditUser(currentState.user)
    }
}