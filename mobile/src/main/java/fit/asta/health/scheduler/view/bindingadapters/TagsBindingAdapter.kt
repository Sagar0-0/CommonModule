package fit.asta.health.scheduler.view.bindingadapters

import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import fit.asta.health.R
import fit.asta.health.scheduler.view.adapters.TagAdapter
import fit.asta.health.scheduler.viewmodel.AlarmViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class TagsBindingAdapter {
    companion object {

        @BindingAdapter(value = ["android:setAdapter", "android:setViewModel"], requireAll = true)
        @JvmStatic
        fun setupRecyclerView(
            recyclerView: RecyclerView,
            tagAdapter: TagAdapter,
            viewModel: AlarmViewModel
        ) {
            val context = recyclerView.context
            recyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = tagAdapter
            viewModel.allTags.observe((context as AppCompatActivity)) {
                tagAdapter.setData(it)
            }
            val itemTouchHelperCallback =
                object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val mList = tagAdapter.getData()
                        val deletedTag = mList[viewHolder.adapterPosition]
//                        var position : Int = viewHolder.adapterPosition
                        viewModel.deleteTag(deletedTag)

                        Snackbar.make(
                            recyclerView,
                            "Deleted ${deletedTag.meta.name}",
                            Snackbar.LENGTH_LONG
                        )
                            .setAction("Undo") {
                                // adding on click listener to our action of snack bar.
                                // below line is to add our item to array list with a position.
                                viewModel.insertTag(deletedTag)
                            }.show()
                    }

                    override fun onChildDraw(
                        c: Canvas,
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        dX: Float,
                        dY: Float,
                        actionState: Int,
                        isCurrentlyActive: Boolean
                    ) {

                        RecyclerViewSwipeDecorator.Builder(
                            c,
                            recyclerView,
                            viewHolder,
                            dX,
                            dY,
                            actionState,
                            isCurrentlyActive
                        )
                            .addBackgroundColor(
                                ContextCompat.getColor(
                                    recyclerView.context,
                                    android.R.color.holo_red_light
                                )
                            )
                            .addActionIcon(R.drawable.ic_baseline_delete_24)
                            .create()
                            .decorate()

                        super.onChildDraw(
                            c,
                            recyclerView,
                            viewHolder,
                            dX,
                            dY,
                            actionState,
                            isCurrentlyActive
                        )
                    }

                }
            val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView)
        }
    }
}