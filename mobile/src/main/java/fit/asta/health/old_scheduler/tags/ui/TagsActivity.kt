package fit.asta.health.old_scheduler.tags.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.appbar.MaterialToolbar
import fit.asta.health.R
import fit.asta.health.old_scheduler.tags.data.ScheduleTagData
import fit.asta.health.old_scheduler.tags.listner.ClickListenerImpl
import fit.asta.health.old_scheduler.tags.listner.TagActivityListener
import fit.asta.health.old_scheduler.tags.viewmodel.TagsViewModel
import javax.inject.Inject


class TagsActivity : AppCompatActivity(), TagActivityListener {

    @Inject
    lateinit var viewTags: TagsView
    private val viewModelTags: TagsViewModel by viewModels()

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

        findViewById<MaterialToolbar>(R.id.tlbTags).setNavigationOnClickListener {
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