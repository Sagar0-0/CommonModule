package fit.asta.health.old.subscription.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import fit.asta.health.R
import fit.asta.health.old.subscription.data.CarouselData
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl


class CarouselFragment : Fragment() {

    private var carousalData: CarouselData? = null

    companion object {

        private const val ARG_CAROUSAL_DATA = "carouselData"
        fun newInstance(carousalData: CarouselData?) = CarouselFragment().apply {

            arguments = Bundle().apply {

                putParcelable(ARG_CAROUSAL_DATA, carousalData)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.subscription_feature_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            carousalData = it.getParcelable(ARG_CAROUSAL_DATA)
            carousalData?.let { data ->

                view.findViewById<TextView>(R.id.txtFeatureTitle).text = data.title
                view.findViewById<TextView>(R.id.txtFeatureDesc).text = data.description
                view.context.showImageByUrl(
                    Uri.parse(getPublicStorageUrl(view.context, data.url)),
                    view.findViewById(R.id.imgFeature)
                )
            }
        }
    }
}