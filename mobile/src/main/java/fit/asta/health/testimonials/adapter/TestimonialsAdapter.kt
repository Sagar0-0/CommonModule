package fit.asta.health.testimonials.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import fit.asta.health.R
import fit.asta.health.common.BaseAdapter
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.testimonials.adapter.viewholder.HeaderViewHolder
import fit.asta.health.testimonials.data.TestimonialData

class TestimonialsAdapter : BaseAdapter<TestimonialData>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<TestimonialData> {
        val layout = R.layout.testimonials_card
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return HeaderViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<TestimonialData>, position: Int) {
        val itemHolder = holder as HeaderViewHolder
        itemHolder.bindData(items[position])
    }
}