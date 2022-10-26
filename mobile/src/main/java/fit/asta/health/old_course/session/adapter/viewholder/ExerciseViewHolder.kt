package fit.asta.health.old_course.session.adapter.viewholder

import android.view.View
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old_course.session.adapter.listeners.OnExerciseClickListener
import fit.asta.health.old_course.session.data.Exercise
import kotlinx.android.synthetic.main.course_session_exercise_card.view.*

class ExerciseViewHolder(
    itemView: View,
    private val onClickListener: OnExerciseClickListener?
) : BaseViewHolder<Exercise>(itemView), View.OnClickListener {

    private var currentItem: Exercise? = null

    init {
        itemView.exercise_frame?.setOnClickListener(this)
    }

    override fun bindData(content: Exercise) {

        currentItem = content

        itemView.txtExerciseTitle.text = content.title
        itemView.txtExerciseSubTitle.text = content.subTitle
        itemView.txtExerciseDuration.text = secToMin(content.duration)
    }

    override fun onClick(view: View) {
        when (view) {
            itemView.exercise_frame -> {
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