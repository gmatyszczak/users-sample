package pl.gmat.users.feature.edit

sealed class EditUserEffect {
    data class InitializeForm(val form: EditUserForm) : EditUserEffect()
    object Finish : EditUserEffect()
}
