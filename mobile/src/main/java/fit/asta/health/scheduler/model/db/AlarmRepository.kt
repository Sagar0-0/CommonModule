package fit.asta.health.scheduler.model.db

import dagger.hilt.android.scopes.ActivityRetainedScoped
import fit.asta.health.network.api.RemoteApis
import fit.asta.health.scheduler.model.LocalDataSource
import javax.inject.Inject

@ActivityRetainedScoped
class AlarmRepository @Inject constructor(
    localDataSource: LocalDataSource,
    remoteApis: RemoteApis
) {
    val local = localDataSource
    val remote = remoteApis
}