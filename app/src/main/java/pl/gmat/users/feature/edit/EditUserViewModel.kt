package pl.gmat.users.feature.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class EditUserViewModel @Inject constructor() : ViewModel() {

    val state = MutableLiveData<EditUserState>().apply {
        value = EditUserState()
    }
    private val currentState get() = state.value ?: EditUserState()

    fun onChooseExistingAddressClicked() {
        state.value = currentState.copy(isAddNewAddressChecked = false)
    }

    fun onAddNewAddressClicked() {
        state.value = currentState.copy(isAddNewAddressChecked = true)
    }
}