package pl.gmat.users.feature.edit.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.gmat.users.common.model.Address
import pl.gmat.users.common.model.User
import pl.gmat.users.common.ui.SingleLiveEvent
import pl.gmat.users.feature.edit.data.EditUserRepository
import pl.gmat.users.feature.edit.mapper.EditUserFormMapper
import pl.gmat.users.feature.edit.model.EditUserForm
import pl.gmat.users.feature.edit.model.EditUserMode

class EditUserViewModel @ViewModelInject constructor(
    @Assisted private val savedState: SavedStateHandle,
    private val repository: EditUserRepository,
    private val mapper: EditUserFormMapper
) : ViewModel() {

    val state = MutableLiveData<EditUserState>().apply { value = EditUserState() }
    val effect = SingleLiveEvent<EditUserEffect>()

    private val currentState get() = state.value ?: EditUserState()
    private val mode: EditUserMode

    init {
        val user = savedState.get<User>(EditUserActivity.EXTRA_USER)
        mode = if (user != null) {
            EditUserMode.Update(user)
        } else {
            EditUserMode.Add
        }
        state.value = currentState.copy(submitButtonTextResId = mode.submitButtonResId)
        if (mode is EditUserMode.Update) {
            val form = mapper.toEditUserForm(mode.user)
            effect.value = EditUserEffect.InitializeForm(form)
            state.value = currentState.copy(addresses = mode.user.addresses)
        }
    }

    fun onAddNewAddressClicked(address: String) {
        val addresses = currentState.addresses
            .toMutableList()
            .apply { add(Address(value = address)) }
        state.value = currentState.copy(addresses = addresses)
        effect.value = EditUserEffect.ClearNewAddressEditText
    }

    fun onRemoveAddressClicked(address: Address) {
        state.value = currentState.copy(
            addresses = currentState.addresses.toMutableList().apply { remove(address) }
        )
    }

    fun onSubmitClicked(form: EditUserForm) = viewModelScope.launch {
        val userId = if (mode is EditUserMode.Update) mode.user.id else null
        val user = mapper.toUser(form, currentState.addresses, userId)
        repository.insertOrUpdateUser(user)
        effect.value = EditUserEffect.Finish
    }
}
