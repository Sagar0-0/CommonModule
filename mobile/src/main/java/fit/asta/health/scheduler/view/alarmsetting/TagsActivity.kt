package fit.asta.health.scheduler.view.alarmsetting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.databinding.ActivityTagsBinding
import fit.asta.health.scheduler.model.db.entity.TagEntity
import fit.asta.health.scheduler.model.net.tag.Data
import fit.asta.health.scheduler.util.Constants
import fit.asta.health.scheduler.view.adapters.TagAdapter
import fit.asta.health.scheduler.view.alarmsetting.bottomsheets.CustomTagInputBottomSheet
import fit.asta.health.scheduler.viewmodel.AlarmSettingViewModel
import fit.asta.health.scheduler.viewmodel.AlarmViewModel
import fit.asta.health.scheduler.viewmodel.SchedulerBackendViewModel
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants.Companion.TAG
import fit.asta.health.utils.NetworkResult
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TagsActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityTagsBinding
    private lateinit var viewModel: AlarmViewModel
    private lateinit var tagAdapter: TagAdapter
    private var mList: List<TagEntity> = emptyList()
    private lateinit var backendViewModel: SchedulerBackendViewModel
    private lateinit var alarmSettingViewModel: AlarmSettingViewModel
    private var offlineListOfTags: MutableList<Data> = ArrayList()
    private var onlineListOfTags: MutableList<Data> = ArrayList()
    private var context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTagsBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        viewModel = ViewModelProvider(this)[AlarmViewModel::class.java]
        backendViewModel = ViewModelProvider(this)[SchedulerBackendViewModel::class.java]
        alarmSettingViewModel = ViewModelProvider(this)[AlarmSettingViewModel::class.java]
        tagAdapter = TagAdapter(mList, viewModel, alarmSettingViewModel)

        backendViewModel.getTagsListFromBackend(Constants.USER_ID)
        backendViewModel.listOfTags.observe(this) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    Log.d(TAG, "onCreate: ${result.data} ${result.message}")
                    lifecycleScope.launch {
//                        viewModel.deleteAllTags()
                        result.data?.data?.forEach { meta ->
                            onlineListOfTags.add(meta)
                        }
                        result.data?.customTagData?.forEach { meta ->
                            onlineListOfTags.add(meta)
                        }

                        val list = viewModel.allTags.value

                        list?.forEach { tag ->
                            offlineListOfTags.add(tag.meta)
                        }

                        offlineListOfTags.containsAll(onlineListOfTags).let { bool ->
                            if (bool) {
                                Log.d(TAG, "onCreate: Same")
                            } else {
                                Log.d(TAG, "onCreate: Diff")
                                onlineListOfTags.minus(offlineListOfTags.toSet()).forEach { tag ->
                                    viewModel.insertTag(TagEntity(false, tag))
                                }
                            }
                        }
                    }
                }
                is NetworkResult.Error -> {
                    Log.d(TAG, "onCreate: ${result.data} ${result.message}")
                }
                is NetworkResult.Loading -> {
                    Log.d(TAG, "onCreate: ${result.data} ${result.message}")
                }
            }
        }



        _binding.adapter = tagAdapter
        _binding.viewModel = viewModel
        _binding.addTagFloatingActionButton.setOnClickListener {
            val customTagInputBottomSheet = CustomTagInputBottomSheet()
//            val bundle = Bundle()
//            bundle.putParcelable("alarmItem", alarmItem)
//            customTagInputBottomSheet.arguments = bundle
            customTagInputBottomSheet.show(
                supportFragmentManager,
                CustomTagInputBottomSheet.TAG
            )
        }

        alarmSettingViewModel.selectedTag.observe(this) { tagItem ->
            Log.d(TAG, "onBindViewHolder: $tagItem")
            var intent = Intent()
            intent.putExtra("TAG_ENTITY", tagItem as Parcelable)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
//
//        _binding.cancelButtonCustomTag.setOnClickListener {
//            _binding.transformationLayout.finishTransform(_binding.parentLayout)
//        }
    }
}