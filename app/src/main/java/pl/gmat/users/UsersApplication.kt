package pl.gmat.users

import android.app.Application
import pl.gmat.users.common.dagger.Injector

class UsersApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Injector.createGraph(this)
    }
}