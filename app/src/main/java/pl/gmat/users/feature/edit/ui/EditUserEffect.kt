package pl.gmat.users.feature.edit.ui

import pl.gmat.users.feature.edit.model.EditUserForm

sealed class EditUserEffect {
    data class InitializeForm(val form: EditUserForm) : EditUserEffect()
    object Finish : EditUserEffect()
    object ClearNewAddressEditText : EditUserEffect()
}
