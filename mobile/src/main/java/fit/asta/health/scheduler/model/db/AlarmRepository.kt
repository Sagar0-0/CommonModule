package fit.asta.health.scheduler.model.db

import dagger.hilt.android.scopes.ActivityRetainedScoped
import fit.asta.health.scheduler.model.LocalDataSource
import fit.asta.health.scheduler.model.api.SchedulerApi
import javax.inject.Inject


@ActivityRetainedScoped
class AlarmRepository @Inject constructor(
    localDataSource: LocalDataSource,
    remoteApis: SchedulerApi
) {
    val local = localDataSource
    val remote = remoteApis
}