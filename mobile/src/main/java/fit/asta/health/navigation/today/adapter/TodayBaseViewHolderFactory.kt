package fit.asta.health.navigation.today.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.navigation.today.adapter.listeners.OnPlanClickListener
import fit.asta.health.navigation.today.adapter.viewholder.*
import fit.asta.health.navigation.today.data.TodayPlanItemData
import fit.asta.health.navigation.today.data.TodayPlanItemType

class TodayBaseViewHolderFactory {

    fun create(
        parent: ViewGroup,
        viewType: Int,
        onClickListener: OnPlanClickListener? = null
    ): BaseViewHolder<TodayPlanItemData> {

        return when (TodayPlanItemType.valueOf(viewType)) {
            TodayPlanItemType.TodayAppointment -> {
                AppointmentViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.today_appointment, parent, false)
                )
            }
            TodayPlanItemType.TodayCourse -> {
                CourseViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.today_course, parent, false),
                    onClickListener
                )
            }
            TodayPlanItemType.TodaySingleReminder -> {

                SingleReminderViewHolder(
                        LayoutInflater.from(parent.context)
                                .inflate(R.layout.today_single_reminder, parent, false)
                )
            }
            TodayPlanItemType.TodayDoubleReminder -> {

                DoubleReminderViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.today_double_reminder, parent, false)
                )
            }

            TodayPlanItemType.TodayWater -> {

                WaterViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.today_water, parent, false)
                )
            }
        }
    }
}