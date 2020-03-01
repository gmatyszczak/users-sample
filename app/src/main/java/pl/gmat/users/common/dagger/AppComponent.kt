package pl.gmat.users.common.dagger

import dagger.BindsInstance
import dagger.Component
import pl.gmat.users.UsersApplication

@Component(
    modules = [AppModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: UsersApplication): AppComponent
    }
}