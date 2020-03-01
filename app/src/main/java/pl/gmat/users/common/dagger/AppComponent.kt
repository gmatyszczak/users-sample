package pl.gmat.users.common.dagger

import dagger.BindsInstance
import dagger.Component
import pl.gmat.users.UsersApplication
import pl.gmat.users.feature.edit.EditUserComponent
import pl.gmat.users.feature.list.UsersListComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent {

    fun usersListComponentFactory(): UsersListComponent.Factory
    fun editUserComponentFactory(): EditUserComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: UsersApplication): AppComponent
    }
}