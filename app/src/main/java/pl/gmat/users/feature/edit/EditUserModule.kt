package pl.gmat.users.feature.edit

import dagger.Binds
import dagger.Module
import pl.gmat.users.common.dagger.ScreenScope

@Module
interface EditUserModule {

    @Binds
    @ScreenScope
    fun bindEditUserRepository(repository: EditUserRepositoryImpl): EditUserRepository
}