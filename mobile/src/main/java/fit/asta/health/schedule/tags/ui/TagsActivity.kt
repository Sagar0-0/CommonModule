package fit.asta.health.schedule.tags.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import fit.asta.health.schedule.tags.data.ScheduleTagData
import fit.asta.health.schedule.tags.listner.ClickListenerImpl
import fit.asta.health.schedule.tags.listner.TagActivityListener
import fit.asta.health.schedule.tags.viewmodel.TagsViewModel
import kotlinx.android.synthetic.main.schedule_tags_activity.*
import org.koin.android.ext.android.inject


class TagsActivity : AppCompatActivity(), TagActivityListener {

    private val viewTags: TagsView by inject()
    private val viewModelTags: TagsViewModel by inject()

    companion object {

        const val TAG_CODE = 20
        const val UID = "uid"
        const val TAG_SELECTED = "selectedTag"

        fun launch(context: Context, selectedTagId: String?) {

            val intent = Intent(context, TagsActivity::class.java)
            intent.putExtra(TAG_SELECTED, selectedTagId)
            ActivityCompat.startActivityForResult(context as Activity, intent, TAG_CODE, null)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(viewTags.setContentView(this))
        //setSupportActionBar(findViewById(R.id.tlbTags))

        val selectedTagId = intent.getStringExtra(TAG_SELECTED)
        viewModelTags.observeTagLiveData(this, TagsObserver(viewTags))
        viewModelTags.fetchTagList(selectedTagId)

        viewTags.fabClickListener(ClickListenerImpl(viewModelTags))
        viewTags.submitClickListener(ClickListenerImpl(viewModelTags))
        viewTags.setAdapterClickListener(ClickListenerImpl(viewModelTags, this))

        tlbTags.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onSelectionResult(selectedData: ScheduleTagData) {

        val localIntent = Intent()
        localIntent.putExtra(TAG_SELECTED, selectedData)
        setResult(Activity.RESULT_OK, localIntent)
        finish()
    }
}