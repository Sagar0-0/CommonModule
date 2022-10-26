package fit.asta.health.old_course.session.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import fit.asta.health.R
import fit.asta.health.common.BaseAdapter
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.old_course.session.adapter.listeners.OnExerciseClickListener
import fit.asta.health.old_course.session.adapter.viewholder.ExerciseViewHolder
import fit.asta.health.old_course.session.data.Exercise


class ExerciseAdapter(private val isVertical: Boolean = true) : BaseAdapter<Exercise>() {

    private var onClickListener: OnExerciseClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Exercise> {
        val layout = R.layout.course_session_exercise_card
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ExerciseViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Exercise>, position: Int) {
        val itemHolder = holder as ExerciseViewHolder
        itemHolder.bindData(items[position])
    }

    fun setAdapterClickListener(listener: OnExerciseClickListener){
        onClickListener = listener
    }
}