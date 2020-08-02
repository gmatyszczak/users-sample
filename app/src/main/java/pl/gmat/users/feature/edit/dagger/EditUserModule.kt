package pl.gmat.users.feature.edit.dagger

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import pl.gmat.users.feature.edit.data.EditUserRepository
import pl.gmat.users.feature.edit.data.EditUserRepositoryImpl
import pl.gmat.users.feature.edit.mapper.EditUserFormMapper
import pl.gmat.users.feature.edit.mapper.EditUserFormMapperImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
class EditUserModule {

    @Provides
    @ActivityRetainedScoped
    fun provideEditUserRepository(repository: EditUserRepositoryImpl): EditUserRepository =
        repository

    @Provides
    @ActivityRetainedScoped
    fun provideEditUserFormMapper(mapper: EditUserFormMapperImpl): EditUserFormMapper = mapper
}