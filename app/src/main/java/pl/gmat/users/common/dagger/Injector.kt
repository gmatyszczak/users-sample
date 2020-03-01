package pl.gmat.users.common.dagger

import pl.gmat.users.UsersApplication

object Injector {

    lateinit var appComponent: AppComponent

    fun createGraph(app: UsersApplication) {
        appComponent = DaggerAppComponent.factory().create(app)
    }
}