package fit.asta.health.scheduler

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.databinding.ActivitySchedulerHomeBinding
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.view.adapters.AlarmAdapter
import fit.asta.health.scheduler.viewmodel.AlarmViewModel
import fit.asta.health.scheduler.viewmodel.SchedulerBackendViewModel
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.TAG
import fit.asta.health.common.utils.NetworkResult

@AndroidEntryPoint
class SchedulerHomeActivity : AppCompatActivity() {

    private lateinit var _binding: ActivitySchedulerHomeBinding
    private lateinit var viewModel: AlarmViewModel
    private lateinit var alarmAdapter: AlarmAdapter
    private var mList: List<AlarmEntity> = emptyList()

    private lateinit var backendViewModel: SchedulerBackendViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        backendViewModel = ViewModelProvider(this)[SchedulerBackendViewModel::class.java]

        _binding = ActivitySchedulerHomeBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        viewModel = ViewModelProvider(this).get(AlarmViewModel::class.java)
        alarmAdapter = AlarmAdapter(mList, viewModel)

        _binding.adapter = alarmAdapter
        _binding.viewModel = viewModel
        _binding.backendViewModel = backendViewModel

        backendViewModel.getListOfSchedules("6309a9379af54f142c65fbff")
        backendViewModel.lisOfSchedules.observe(this) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    Log.d(TAG, "onCreate list result: ${result.data}")
                    val offlineSchedules = viewModel.allAlarms.value
                    val onlineSchedules = result.data?.data

                    val offlineIds = ArrayList<String>()
                    val onlineIds = ArrayList<String>()

                    onlineSchedules?.forEach {
                        onlineIds.add(it.idFromServer)
                    }

                    offlineSchedules?.forEach {
                        offlineIds.add(it.idFromServer)
                    }

                    Log.d(TAG, "onlineIds: $onlineIds")
                    Log.d(TAG, "offlineIds: $offlineIds")
                    Log.d(TAG, "minus: ${onlineIds.minus(offlineIds.toSet())}")

                    onlineIds.minus(offlineIds.toSet()).forEach { id ->
                        onlineSchedules?.forEach { entity ->
                            if (entity.idFromServer == id) {
                                viewModel.insertAlarm(entity)
                            }
                        }
                    }
                }
                is NetworkResult.Error -> {
                    Log.d(TAG, "onCreate list result: ${result.data}")
                }
                is NetworkResult.Loading -> {
                    Log.d(TAG, "onCreate list result: ${result.data}")
                }
            }
        }
    }
}