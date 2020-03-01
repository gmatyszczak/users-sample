package pl.gmat.users.feature.details

import dagger.BindsInstance
import dagger.Subcomponent
import pl.gmat.users.common.dagger.ScreenScope

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