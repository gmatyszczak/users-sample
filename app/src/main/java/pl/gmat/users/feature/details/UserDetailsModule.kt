package pl.gmat.users.feature.details

import dagger.Binds
import dagger.Module
import pl.gmat.users.common.dagger.ScreenScope

@Module
interface UserDetailsModule {

    @Binds
    @ScreenScope
    fun bindUsersListRepository(repository: UserDetailsRepositoryImpl): UserDetailsRepository
}