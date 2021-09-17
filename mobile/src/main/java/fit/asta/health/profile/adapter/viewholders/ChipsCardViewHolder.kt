package fit.asta.health.profile.adapter.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.google.android.material.chip.Chip
import fit.asta.health.R
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.profile.adapter.ChipCardItem
import fit.asta.health.profile.adapter.ProfileItem
import fit.asta.health.profile.listener.OnChipActionListener
import kotlinx.android.synthetic.main.chips_card_item.view.*


class ChipsCardViewHolder(itemView: View, val listener: OnChipActionListener?): BaseViewHolder<ProfileItem>(itemView) {
    override fun bindData(content: ProfileItem) {

        if (content is ChipCardItem) {
            itemView.txtTitle.text = content.label
            val inflater = LayoutInflater.from(itemView.context)

            itemView.chipGroup.removeAllViews()

            content.value.forEach {
                val chip = inflater.inflate(
                    R.layout.chip_layout,
                    itemView.chipGroup, false
                ) as Chip
                chip.text = it.value
                chip.isClickable = true
                chip.isFocusable = true
                chip.setOnCloseIconClickListener {
                    chipClickListener(it, content)
                }
                itemView.chipGroup.addView(chip)
            }
            incrementChip(content)
           }
        }
    private fun incrementChip(content: ChipCardItem) {
        val addChip = LayoutInflater.from(itemView.context).inflate(R.layout.add_chip,
            itemView.chipGroup, false) as Chip
        val view = itemView.chipGroup.findViewById<Chip>(R.id.addedChip)
        if (view == null) {
            addChip.visibility = View.VISIBLE
            itemView.chipGroup.addView(addChip)
        }
        addChip.setOnCloseIconClickListener {
            listener?.onChipAdd(content)
        }
    }

    private  fun chipClickListener(view: View, content: ChipCardItem) {

        val animation = AlphaAnimation(1f,0f)
        animation.duration = 250
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                listener?.onChipDelete(content)
                itemView.chipGroup.removeView(view)
             }
        })
        view.startAnimation(animation)
    }
}