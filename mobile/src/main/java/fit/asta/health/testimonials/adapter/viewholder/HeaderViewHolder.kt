package fit.asta.health.testimonials.adapter.viewholder

import android.annotation.SuppressLint
import android.net.Uri
import android.view.View
import fit.asta.health.common.BaseViewHolder
import fit.asta.health.testimonials.data.TestimonialData
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl
import kotlinx.android.synthetic.main.testimonials_card.view.*

class HeaderViewHolder(
    viewItem: View
) : BaseViewHolder<TestimonialData>(viewItem) {

    private var currentItem: TestimonialData? = null

    @SuppressLint("SetTextI18n")
    override fun bindData(content: TestimonialData) {

        currentItem = content

        itemView.context.showImageByUrl(
            Uri.parse(getPublicStorageUrl(itemView.context, content.imageURL)),
            itemView.imgTestimonials1
        )
        itemView.txtTestimonials1.text = content.testimonial
        itemView.name1.text = content.name
        itemView.cto1.text = "${content.designation}, ${content.organization}"
    }
}
