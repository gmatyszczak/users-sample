package pl.gmat.users.feature.list.dagger

import dagger.Binds
import dagger.Module
import pl.gmat.users.common.dagger.ScreenScope
import pl.gmat.users.feature.list.data.UsersListRepository
import pl.gmat.users.feature.list.data.UsersListRepositoryImpl

@Module
interface UsersListModule {

    @Binds
    @ScreenScope
    fun bindUsersListRepository(repository: UsersListRepositoryImpl): UsersListRepository
}