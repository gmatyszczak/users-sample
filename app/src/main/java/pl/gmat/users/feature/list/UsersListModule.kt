package pl.gmat.users.feature.list

import dagger.Binds
import dagger.Module
import pl.gmat.users.common.dagger.ScreenScope

@Module
interface UsersListModule {

    @Binds
    @ScreenScope
    fun bindUsersListRepository(repository: UsersListRepositoryImpl): UsersListRepository
}