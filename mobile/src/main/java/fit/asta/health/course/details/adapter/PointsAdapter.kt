package fit.asta.health.course.details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.R
import fit.asta.health.utils.GenericAdapter
import kotlinx.android.synthetic.main.course_point.view.*


class PointsAdapter(val context: Context, items: List<String>) :
    GenericAdapter<String>(items) {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(item: String) {

            itemView.txtCourseBullet.text = item
        }
    }

    override fun setViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.course_point, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, item: String, pos: Int) {

        val itemHolder = holder as ItemViewHolder
        itemHolder.setData(item)
    }
}