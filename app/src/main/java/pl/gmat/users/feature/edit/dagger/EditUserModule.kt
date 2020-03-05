package pl.gmat.users.feature.edit.dagger

import dagger.Module
import dagger.Provides
import pl.gmat.users.common.dagger.ScreenScope
import pl.gmat.users.common.model.User
import pl.gmat.users.feature.edit.data.EditUserRepository
import pl.gmat.users.feature.edit.data.EditUserRepositoryImpl
import pl.gmat.users.feature.edit.mapper.EditUserFormMapper
import pl.gmat.users.feature.edit.mapper.EditUserFormMapperImpl
import pl.gmat.users.feature.edit.model.EditUserMode
import pl.gmat.users.feature.edit.ui.EditUserActivity

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