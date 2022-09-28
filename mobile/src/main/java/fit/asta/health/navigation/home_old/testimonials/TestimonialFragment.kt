package fit.asta.health.navigation.home_old.testimonials

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fit.asta.health.R
import fit.asta.health.testimonials.data.TestimonialData
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl
import kotlinx.android.synthetic.main.testimonials_card.view.*

class TestimonialFragment : Fragment() {

    private var testimonialData: TestimonialData? = null

    companion object {

        private const val ARG_CAROUSAL_DATA = "testimonialData"
        fun newInstance(testimonialData: TestimonialData?) = TestimonialFragment().apply {

            arguments = Bundle().apply {

                putParcelable(ARG_CAROUSAL_DATA, testimonialData)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {

            testimonialData = it.getParcelable(ARG_CAROUSAL_DATA)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.testimonials_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            testimonialData = it.getParcelable(ARG_CAROUSAL_DATA)
            if (testimonialData != null) {

                view.context.showImageByUrl(
                    Uri.parse(getPublicStorageUrl(view.context, testimonialData?.imageURL!!)),
                    view.imgTestimonials1
                )
                view.txtTestimonials1.text = testimonialData?.testimonial
                view.name1.text = testimonialData?.name
                view.cto1.text = "${testimonialData?.designation}, ${testimonialData?.organization}"
            }
        }
    }
}