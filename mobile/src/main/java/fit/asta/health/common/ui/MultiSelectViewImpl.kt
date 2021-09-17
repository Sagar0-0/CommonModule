package fit.asta.health.common.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fit.asta.health.R
import fit.asta.health.common.adapter.MultiSelectAdapter
import fit.asta.health.common.adapter.SelectionUpdateListener
import fit.asta.health.common.data.MultiSelectData
import kotlinx.android.synthetic.main.multi_selection_activity.view.*

class MultiSelectViewImpl: MultiSelectView {

    private var rootView: View? = null

    override fun setContentView(activity: Activity): View? {
        rootView = LayoutInflater.from(activity).inflate(
            R.layout.multi_selection_activity,null,false)
        initializeViews()
        return rootView
    }

    private fun initializeViews() {

        rootView?.let {
            val adapter = MultiSelectAdapter()
            it.recyclerView.layoutManager = LinearLayoutManager(it.context)
            it.recyclerView.addItemDecoration(DividerItemDecoration(it.context,
                LinearLayoutManager.VERTICAL))
            it.recyclerView.adapter = adapter
        }

    }

    override fun changeState(state: MultiSelectView.State) {
       when(state){
           is MultiSelectView.State.LoadSelection -> {
               setAdapter(state.list)
           }

           MultiSelectView.State.Empty -> {

           }

          is  MultiSelectView.State.Error -> {

           }

       }
    }

    private fun setAdapter(list: List<MultiSelectData>) {
        rootView?.let {
            (it.recyclerView.adapter as MultiSelectAdapter).updateList(list)
        }
    }

    override fun saveListener(listener: View.OnClickListener) {

    }

    override fun setAdapterClickListener(listener: SelectionUpdateListener) {
        rootView?.let {
            (it.recyclerView.adapter as MultiSelectAdapter).setAdapterListener(listener)
        }
    }
}