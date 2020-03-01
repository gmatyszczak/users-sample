package pl.gmat.users.feature.edit

import dagger.BindsInstance
import dagger.Subcomponent
import pl.gmat.users.common.dagger.ScreenScope

@ScreenScope
@Subcomponent(
    modules = [EditUserModule::class]
)
interface EditUserComponent {

    fun inject(activity: EditUserActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance activity: EditUserActivity): EditUserComponent
    }
}