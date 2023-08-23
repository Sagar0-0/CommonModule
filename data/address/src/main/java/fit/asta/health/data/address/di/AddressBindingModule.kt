package fit.asta.health.data.address.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
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