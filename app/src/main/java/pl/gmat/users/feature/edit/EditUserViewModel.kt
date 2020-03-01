package pl.gmat.users.feature.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.gmat.users.common.SingleLiveEvent
import pl.gmat.users.common.model.Address
import pl.gmat.users.common.model.Gender
import pl.gmat.users.common.model.User
import javax.inject.Inject

class EditUserViewModel @Inject constructor(
    private val repository: EditUserRepository
) : ViewModel() {

    val state = MutableLiveData<EditUserState>().apply { value = EditUserState() }
    val effect = SingleLiveEvent<EditUserEffect>()

    private val currentState get() = state.value ?: EditUserState()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            state.value = currentState.copy(addresses = repository.loadAddresses())
        }
    }

    fun onChooseExistingAddressClicked() {
        state.value = currentState.copy(isAddNewAddressChecked = false)
    }

    fun onAddNewAddressClicked() {
        state.value = currentState.copy(isAddNewAddressChecked = true)
    }

    fun onAddClicked(form: EditUserForm) = viewModelScope.launch(Dispatchers.Main) {
        repository.addUser(form.toUser(), currentState.isAddNewAddressChecked)
        effect.value = EditUserEffect.Finish
    }

    private fun EditUserForm.toUser(): User {
        val address =
            if (currentState.isAddNewAddressChecked) {
                Address(value = newAddress)
            } else {
                currentState.addresses[existingAddressIndex]
            }
        return User(
            firstName = firstName,
            lastName = lastName,
            age = age,
            gender = Gender.values()[genderIndex],
            address = address
        )
    }
}
