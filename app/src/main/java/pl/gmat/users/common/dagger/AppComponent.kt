package pl.gmat.users.common.dagger

import dagger.BindsInstance
import dagger.Component
import pl.gmat.users.UsersApplication
import pl.gmat.users.feature.details.dagger.UserDetailsComponent
import pl.gmat.users.feature.edit.dagger.EditUserComponent
import pl.gmat.users.feature.list.dagger.UsersListComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent {

    fun usersListComponentFactory(): UsersListComponent.Factory
    fun editUserComponentFactory(): EditUserComponent.Factory
    fun userDetailsComponentFactory(): UserDetailsComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: UsersApplication): AppComponent
    }
}