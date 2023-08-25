package fit.asta.health.navigation.home.di

import android.content.Context
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fit.asta.health.navigation.home.model.ToolsHomeRepo
import fit.asta.health.navigation.home.model.ToolsHomeRepoImpl
import fit.asta.health.navigation.home.model.api.ToolsApi
import fit.asta.health.navigation.home.model.api.ToolsRestApi
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Singleton
    @Provides
    fun provideToolsApi(client: OkHttpClient): ToolsApi {
        return ToolsRestApi(client)
    }

    @Singleton
    @Provides
    fun provideHomeToolsRep(toolsApi: ToolsApi): ToolsHomeRepo {
        return ToolsHomeRepoImpl(toolsApi = toolsApi)
    }

    @Provides
    @Singleton
    fun providesReviewManager(@ApplicationContext context: Context): ReviewManager {
        return ReviewManagerFactory.create(context)
    }
}

