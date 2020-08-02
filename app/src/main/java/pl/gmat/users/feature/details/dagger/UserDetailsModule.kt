package pl.gmat.users.feature.details.dagger

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import pl.gmat.users.feature.details.data.UserDetailsRepository
import pl.gmat.users.feature.details.data.UserDetailsRepositoryImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
class UserDetailsModule {

    @Provides
    @ActivityRetainedScoped
    fun provideUsersListRepository(repository: UserDetailsRepositoryImpl): UserDetailsRepository =
        repository
}
