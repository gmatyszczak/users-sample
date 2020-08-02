package pl.gmat.users.feature.list.dagger

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import pl.gmat.users.feature.list.data.UsersListRepository
import pl.gmat.users.feature.list.data.UsersListRepositoryImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
interface UsersListModule {

    @Binds
    @ActivityRetainedScoped
    fun bindUsersListRepository(repository: UsersListRepositoryImpl): UsersListRepository
}