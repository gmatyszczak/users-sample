package pl.gmat.users.feature.edit

import dagger.Module
import dagger.Provides
import pl.gmat.users.common.dagger.ScreenScope
import pl.gmat.users.common.model.User

@Module
class EditUserModule {

    @Provides
    @ScreenScope
    fun provideEditUserRepository(repository: EditUserRepositoryImpl): EditUserRepository =
        repository

    @Provides
    @ScreenScope
    fun provideEditUserMode(activity: EditUserActivity): EditUserMode {
        val user = activity.intent.getParcelableExtra<User>(EditUserActivity.EXTRA_USER)
        return if (user != null) {
            EditUserMode.Update(user)
        } else {
            EditUserMode.Add
        }
    }

    @Provides
    @ScreenScope
    fun provideEditUserFormMapper(mapper: EditUserFormMapperImpl): EditUserFormMapper = mapper
}