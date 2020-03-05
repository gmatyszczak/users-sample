package pl.gmat.users.feature.list.dagger

import dagger.BindsInstance
import dagger.Subcomponent
import pl.gmat.users.common.dagger.ScreenScope
import pl.gmat.users.feature.list.ui.UsersListActivity

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