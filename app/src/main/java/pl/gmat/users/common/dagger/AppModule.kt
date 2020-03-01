package pl.gmat.users.common.dagger

import androidx.room.Room
import dagger.Module
import dagger.Provides
import pl.gmat.users.UsersApplication
import pl.gmat.users.common.database.UsersDatabase
import pl.gmat.users.common.database.dao.AddressDao
import pl.gmat.users.common.database.dao.UserDao
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(application: UsersApplication): UsersDatabase =
        Room.databaseBuilder(application, UsersDatabase::class.java, "users.db")
            .fallbackToDestructiveMigration() // TODO for simplified development, remove before release
            .build()

    @Provides
    @Singleton
    fun provideUserDao(database: UsersDatabase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun provideAddressDao(database: UsersDatabase): AddressDao = database.addressDao()
}