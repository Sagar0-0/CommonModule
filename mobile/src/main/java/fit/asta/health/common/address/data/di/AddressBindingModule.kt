package fit.asta.health.common.address.data.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import fit.asta.health.MainActivity
import fit.asta.health.common.address.data.repo.AddressRepo
import fit.asta.health.common.address.data.repo.AddressRepoImpl
import javax.inject.Qualifier

@Module
@InstallIn(ActivityComponent::class)
abstract class AddressBindingModule {

    @Binds
    @MyContext
    abstract fun getContext(@ActivityContext context: Context): Context
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MyContext