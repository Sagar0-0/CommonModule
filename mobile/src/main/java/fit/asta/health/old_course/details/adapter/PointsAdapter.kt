package fit.asta.health.old_course.details.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.R
import fit.asta.health.utils.GenericAdapter


class PointsAdapter(val context: Context, items: List<String>) :
    GenericAdapter<String>(items) {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setData(item: String) {

            itemView.findViewById<AppCompatTextView>(R.id.txtCourseBullet).text = item
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