package pl.gmat.users.feature.details.dagger

import dagger.BindsInstance
import dagger.Subcomponent
import pl.gmat.users.common.dagger.ScreenScope
import pl.gmat.users.feature.details.ui.UserDetailsActivity

@ScreenScope
@Subcomponent(
    modules = [UserDetailsModule::class]
)
interface UserDetailsComponent {

    fun inject(activity: UserDetailsActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance activity: UserDetailsActivity): UserDetailsComponent
    }
}