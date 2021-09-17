package fit.asta.health.subscription.listner

import android.view.View
import fit.asta.health.R
import fit.asta.health.subscription.viewmodel.SubscriptionViewModel
import fit.asta.health.utils.getPublicStorageUrl
import fit.asta.health.utils.showUrlInBrowser

class TermsClickListenerImpl(val viewModel: SubscriptionViewModel) : View.OnClickListener {
    override fun onClick(view: View) {

        view.let {
            it.context.showUrlInBrowser(
                getPublicStorageUrl(it.context, it.resources.getString(R.string.url_terms_of_use))
            )
        }
    }
}
