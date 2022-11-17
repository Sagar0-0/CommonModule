package fit.asta.health.thirdparty.spotify.model.db

import dagger.hilt.android.scopes.ActivityRetainedScoped
import fit.asta.health.thirdparty.spotify.model.LocalDataSource
import javax.inject.Inject

@ActivityRetainedScoped
class MusicRepository @Inject constructor(
    localDataSource: LocalDataSource
) {
    val local = localDataSource
}