package pl.gmat.users.feature.details.dagger

import dagger.Module
import dagger.Provides
import pl.gmat.users.common.dagger.ScreenScope
import pl.gmat.users.feature.details.data.UserDetailsRepository
import pl.gmat.users.feature.details.data.UserDetailsRepositoryImpl
import pl.gmat.users.feature.details.ui.UserDetailsActivity

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