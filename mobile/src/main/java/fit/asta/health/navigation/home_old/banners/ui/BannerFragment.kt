package fit.asta.health.navigation.home_old.banners.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fit.asta.health.R
import fit.asta.health.navigation.home_old.banners.data.BannerData
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showImageByUrl
import kotlinx.android.synthetic.main.home_banner_image_card.view.*


class BannerFragment : Fragment() {

    private var bannerData: BannerData? = null

    companion object {

        private const val ARG_CAROUSAL_DATA = "bannerData"
        fun newInstance(testimonialData: BannerData?) = BannerFragment().apply {

            arguments = Bundle().apply {

                putParcelable(ARG_CAROUSAL_DATA, testimonialData)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.home_banner_image_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            bannerData = it.getParcelable(ARG_CAROUSAL_DATA)
            bannerData?.let { data ->
                view.context.showImageByUrl(
                    Uri.parse(getPublicStorageUrl(view.context, data.url)),
                    view.imgHomeBanner
                )
                //view.titleHomeBanner.text = data.title
                // view.subTitleHomeBanner.text = data.subTitle
            }
        }
    }
}