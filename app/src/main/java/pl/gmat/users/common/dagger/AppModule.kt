package pl.gmat.users.common.dagger

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import pl.gmat.users.common.database.UsersDatabase
import pl.gmat.users.common.database.dao.AddressDao
import pl.gmat.users.common.database.dao.UserDao
import pl.gmat.users.common.mapper.UserMapper
import pl.gmat.users.common.mapper.UserMapperImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): UsersDatabase =
        Room.databaseBuilder(context, UsersDatabase::class.java, "users.db")
            .build()

    @Provides
    @Singleton
    fun provideUserDao(database: UsersDatabase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun provideAddressDao(database: UsersDatabase): AddressDao = database.addressDao()

    @Provides
    @Reusable
    fun provideUserMapper(mapper: UserMapperImpl): UserMapper = mapper
}