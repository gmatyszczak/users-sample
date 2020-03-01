package pl.gmat.users.feature.list

import androidx.lifecycle.ViewModel
import pl.gmat.users.common.SingleLiveEvent
import javax.inject.Inject

class UsersListViewModel @Inject constructor() : ViewModel() {

    val effect = SingleLiveEvent<UsersListEffect>()

    fun onAddClicked() {
        effect.value = UsersListEffect.ShowAddUser
    }

    fun onDeleteAllClicked() = Unit
}