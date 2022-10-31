package fit.asta.health.common.multiselect.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import fit.asta.health.R
import fit.asta.health.common.multiselect.MultiSelectObserver
import fit.asta.health.common.multiselect.adapter.SelectionUpdateListenerImpl
import fit.asta.health.common.multiselect.viewmodel.MultiSelectViewModel
import org.koin.android.ext.android.inject


class MultiSelectActivity : AppCompatActivity() {

    private val multiSelectView: MultiSelectView by inject()
    private val viewModel: MultiSelectViewModel by inject()

    companion object {

        const val UID = "uid"
        const val SELECTED_DATA = "selected_data"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(multiSelectView.setContentView(this))
        viewModel.setLiveDataObservable(this, MultiSelectObserver(multiSelectView))
        multiSelectView.setAdapterClickListener(SelectionUpdateListenerImpl(viewModel))
        viewModel.getMultiSelectData(intent.getParcelableExtra(UID))
        findViewById<ExtendedFloatingActionButton>(R.id.saveButton).setOnClickListener {
            val localIntent = Intent()
            val selectedData = viewModel.getSelectedData(intent.getParcelableExtra(UID))
            localIntent.putExtra(SELECTED_DATA, selectedData)
            setResult(Activity.RESULT_OK, localIntent)
            finish()
        }
    }
}