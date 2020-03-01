package pl.gmat.users.feature.list

import dagger.BindsInstance
import dagger.Subcomponent
import pl.gmat.users.common.dagger.ScreenScope

@ScreenScope
@Subcomponent(
    modules = [UsersListModule::class]
)
interface UsersListComponent {

    fun inject(activity: UsersListActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance activity: UsersListActivity): UsersListComponent
    }
}