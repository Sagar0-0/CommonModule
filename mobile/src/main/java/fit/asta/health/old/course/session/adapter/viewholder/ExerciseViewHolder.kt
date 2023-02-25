package fit.asta.health.old.course.session.adapter.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.card.MaterialCardView
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old.course.session.adapter.listeners.OnExerciseClickListener
import fit.asta.health.old.course.session.data.Exercise


class ExerciseViewHolder(
    itemView: View,
    private val onClickListener: OnExerciseClickListener?
) : BaseViewHolder<Exercise>(itemView), View.OnClickListener {

    private var currentItem: Exercise? = null

    init {
        itemView.findViewById<MaterialCardView>(R.id.exercise_frame)?.setOnClickListener(this)
    }

    override fun bindData(content: Exercise) {

        currentItem = content

        itemView.findViewById<AppCompatTextView>(R.id.txtExerciseTitle).text = content.title
        itemView.findViewById<AppCompatTextView>(R.id.txtExerciseSubTitle).text = content.subTitle
        itemView.findViewById<AppCompatTextView>(R.id.txtExerciseDuration).text =
            secToMin(content.duration)
    }

    override fun onClick(view: View) {
        when (view) {
            itemView.findViewById<MaterialCardView>(R.id.exercise_frame) -> {
                onClickListener?.onExerciseClick(layoutPosition)
            }
        }
    }

    private fun secToMin(secs: Long): String {
        return if (secs < 60) "$secs ${itemView.context.getString(R.string.title_sec)}"
        else "${
            (secs / 60).toString().padStart(2, '0')
        } ${itemView.context.getString(R.string.title_min)}"
    }
}