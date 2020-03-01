package pl.gmat.users.feature.details

import dagger.Module
import dagger.Provides
import pl.gmat.users.common.dagger.ScreenScope

@Module
class UserDetailsModule {

    @Provides
    @ScreenScope
    fun provideUsersListRepository(repository: UserDetailsRepositoryImpl): UserDetailsRepository =
        repository

    @Provides
    @ScreenScope
    fun provideUserId(activity: UserDetailsActivity): Long =
        activity.intent.getLongExtra(UserDetailsActivity.EXTRA_USER_ID, -1)
}