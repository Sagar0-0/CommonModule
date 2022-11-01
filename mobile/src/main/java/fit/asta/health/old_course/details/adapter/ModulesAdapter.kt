package fit.asta.health.old_course.details.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import fit.asta.health.ActivityLauncher
import fit.asta.health.R
import fit.asta.health.old_course.details.data.SessionData
import fit.asta.health.utils.GenericAdapter
import org.koin.core.KoinComponent
import org.koin.core.inject


class ModulesAdapter(val context: Context, val courseId: String, items: List<SessionData>) :
    GenericAdapter<SessionData>(items), KoinComponent {

    private val launcher: ActivityLauncher by inject()

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var currentItem: SessionData? = null

        init {

            itemView.findViewById<MaterialButton>(R.id.btnModulePlay)?.setOnClickListener {

                launcher.launchVideoPlayerActivity(context, courseId, currentItem?.uid!!)
            }
        }

        @SuppressLint("SetTextI18n")
        fun setData(item: SessionData) {

            this.currentItem = item

            /*val url = getPublicStorageUrl(context, metaData?.imgLoc + item.url + ".jpg")
            context.showCircleImageByUrl(Uri.parse(url), itemView.img_course_expert)*/

            itemView.findViewById<AppCompatTextView>(R.id.txtModuleStatusTitle).visibility =
                View.VISIBLE
            itemView.findViewById<AppCompatImageView>(R.id.imgModuleStatus).visibility =
                View.VISIBLE

            itemView.findViewById<AppCompatTextView>(R.id.txtModuleTitle).text = item.title
            itemView.findViewById<AppCompatTextView>(R.id.txtModuleSubtitle).text = item.subTitle
            itemView.findViewById<AppCompatTextView>(R.id.txtModuleLevel).text = item.level
            itemView.findViewById<AppCompatTextView>(R.id.txtModuleDuration).text =
                "${item.duration} ${context.resources.getString(R.string.title_min)}"
            itemView.findViewById<AppCompatRatingBar>(R.id.ragingModuleIntensity).rating =
                item.intensity
            itemView.findViewById<AppCompatTextView>(R.id.txtModuleCalories).text =
                item.calories.toString()
            itemView.findViewById<AppCompatTextView>(R.id.txtModuleDesc).text = item.desc

            if (item.precautions.isNotEmpty()) {

                itemView.findViewById<AppCompatTextView>(R.id.txtModulePrecautions).visibility =
                    View.VISIBLE
                val rcvPrecautions = itemView.findViewById<RecyclerView>(R.id.rcvPrecautions)
                rcvPrecautions.visibility = View.VISIBLE
                rcvPrecautions.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                rcvPrecautions.adapter = PointsAdapter(
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