package pl.gmat.users.feature.edit.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.gmat.users.common.ui.SingleLiveEvent
import pl.gmat.users.feature.edit.data.EditUserRepository
import pl.gmat.users.feature.edit.mapper.EditUserFormMapper
import pl.gmat.users.feature.edit.model.EditUserForm
import pl.gmat.users.feature.edit.model.EditUserMode
import javax.inject.Inject

class EditUserViewModel @Inject constructor(
    private val repository: EditUserRepository,
    private val mode: EditUserMode,
    private val mapper: EditUserFormMapper
) : ViewModel() {

    val state = MutableLiveData<EditUserState>().apply { value =
        EditUserState()
    }
    val effect = SingleLiveEvent<EditUserEffect>()

    private val currentState get() = state.value ?: EditUserState()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            state.value = currentState.copy(
                submitButtonTextResId = mode.submitButtonResId
            )
            if (mode is EditUserMode.Update) {
                val form = mapper.toEditUserForm(mode.user)
                effect.value = EditUserEffect.InitializeForm(form)
            }
        }
    }

    fun onSubmitClicked(form: EditUserForm) = viewModelScope.launch(Dispatchers.Main) {
        val userId = if (mode is EditUserMode.Update) mode.user.id else null
        val user = mapper.toUser(form, userId)
        repository.insertOrUpdateUser(user)
        effect.value = EditUserEffect.Finish
    }
}
