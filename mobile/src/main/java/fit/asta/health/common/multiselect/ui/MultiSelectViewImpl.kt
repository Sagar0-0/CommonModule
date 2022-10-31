package fit.asta.health.common.multiselect.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.R
import fit.asta.health.common.multiselect.adapter.MultiSelectAdapter
import fit.asta.health.common.multiselect.adapter.SelectionUpdateListener
import fit.asta.health.common.multiselect.data.MultiSelectData

class MultiSelectViewImpl : MultiSelectView {

    private var rootView: View? = null

    override fun setContentView(activity: Activity): View? {
        rootView = LayoutInflater.from(activity).inflate(
            R.layout.listview_multiselect_save_activity, null, false
        )
        initializeViews()
        return rootView
    }

    private fun initializeViews() {

        rootView?.let {
            val adapter = MultiSelectAdapter()
            val recyclerView = it.findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(it.context)
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    it.context,
                    LinearLayoutManager.VERTICAL
                )
            )
            recyclerView.adapter = adapter
        }

    }

    override fun changeState(state: MultiSelectView.State) {
        when (state) {
            is MultiSelectView.State.LoadSelection -> {
                setAdapter(state.list)
            }

            MultiSelectView.State.Empty -> {

            }

            is MultiSelectView.State.Error -> {

            }

        }
    }

    private fun setAdapter(list: List<MultiSelectData>) {
        rootView?.let {
            val recyclerView = it.findViewById<RecyclerView>(R.id.recyclerView)
            (recyclerView.adapter as MultiSelectAdapter).updateList(list)
        }
    }

    override fun saveListener(listener: View.OnClickListener) {

    }

    override fun setAdapterClickListener(listener: SelectionUpdateListener) {
        rootView?.let {
            val recyclerView = it.findViewById<RecyclerView>(R.id.recyclerView)
            (recyclerView.adapter as MultiSelectAdapter).setAdapterListener(listener)
        }
    }
}