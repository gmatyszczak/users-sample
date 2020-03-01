package pl.gmat.users.feature.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.gmat.users.common.SingleLiveEvent
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(
    private val userDetailsRepository: UserDetailsRepository
) : ViewModel() {

    val state = MutableLiveData<UserDetailsState>().apply { value = UserDetailsState() }
    val effect = SingleLiveEvent<UserDetailsEffect>()

    private val currentState get() = state.value ?: UserDetailsState()
}