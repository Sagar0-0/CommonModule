package fit.asta.health.old_course.details.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fit.asta.health.ActivityLauncher
import fit.asta.health.R
import fit.asta.health.old_course.details.data.SessionData
import fit.asta.health.utils.GenericAdapter
import kotlinx.android.synthetic.main.course_module.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject


class ModulesAdapter(val context: Context, val courseId: String, items: List<SessionData>) :
    GenericAdapter<SessionData>(items), KoinComponent {

    private val launcher: ActivityLauncher by inject()

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var currentItem: SessionData? = null

        init {

            itemView.btnModulePlay?.setOnClickListener {

                launcher.launchVideoPlayerActivity(context, courseId, currentItem?.uid!!)
            }
        }

        @SuppressLint("SetTextI18n")
        fun setData(item: SessionData) {

            this.currentItem = item

            /*val url = getPublicStorageUrl(context, metaData?.imgLoc + item.url + ".jpg")
            context.showCircleImageByUrl(Uri.parse(url), itemView.img_course_expert)*/

            itemView.txtModuleStatusTitle.visibility = View.VISIBLE
            itemView.imgModuleStatus.visibility = View.VISIBLE

            itemView.txtModuleTitle.text = item.title
            itemView.txtModuleSubtitle.text = item.subTitle
            itemView.txtModuleLevel.text = item.level
            itemView.txtModuleDuration.text =
                "${item.duration} ${context.resources.getString(R.string.title_min)}"
            itemView.ragingModuleIntensity.rating = item.intensity
            itemView.txtModuleCalories.text = item.calories.toString()
            itemView.txtModuleDesc.text = item.desc

            if (item.precautions.isNotEmpty()) {

                itemView.txtModulePrecautions.visibility = View.VISIBLE
                itemView.rcvPrecautions.visibility = View.VISIBLE
                itemView.rcvPrecautions.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                itemView.rcvPrecautions.adapter =
                    PointsAdapter(
                        itemView.context,
                        item.precautions
                    )
            }
        }
    }

    override fun setViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.course_module, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, item: SessionData, pos: Int) {

        val itemHolder = holder as ItemViewHolder
        itemHolder.setData(item)
    }
}