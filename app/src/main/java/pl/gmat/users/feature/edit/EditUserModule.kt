package pl.gmat.users.feature.edit

import dagger.Binds
import dagger.Module

@Module
interface EditUserModule {

    @Binds
    fun bindEditUserRepository(repository: EditUserRepositoryImpl): EditUserRepository
}