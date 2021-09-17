package fit.asta.health.course.details.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.R
import fit.asta.health.course.details.data.ExpertData
import fit.asta.health.utils.GenericAdapter
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showCircleImageByUrl
import kotlinx.android.synthetic.main.course_expert.view.*


class ExpertsAdapter(val context: Context, items: List<ExpertData>) :
    GenericAdapter<ExpertData>(items) {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun setData(expert: ExpertData) {

            val url = getPublicStorageUrl(context, expert.url)
            context.showCircleImageByUrl(Uri.parse(url), itemView.imgCourseExpert)

            itemView.txtCourseExpertName.text = expert.name
            itemView.txtCourseExpertExp.text =
                "${expert.expYears} ${context.resources.getString(R.string.title_yrs_of_exp)}"
            itemView.txtCourseExpertPfn.text = expert.profession
            itemView.txtCourseExpertQlf.text = expert.qualification
            itemView.txtCourseAboutExpert.text = expert.about
        }
    }

    override fun setViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.course_expert, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, item: ExpertData, pos: Int) {

        val itemHolder = holder as ItemViewHolder
        itemHolder.setData(item)
    }
}