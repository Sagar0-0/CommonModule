package fit.asta.health.old_subscription.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import fit.asta.health.old_subscription.listner.PrivacyClickListenerImpl
import fit.asta.health.old_subscription.listner.SubPlanSelectionListenerImpl
import fit.asta.health.old_subscription.listner.TermsClickListenerImpl
import fit.asta.health.old_subscription.viewmodel.SubscriptionViewModel
import org.koin.android.ext.android.inject

class SubscriptionFragment(private val tabName: String) : Fragment() {

    private val subscriptionView: SubscriptionView by inject()
    private val subscriptionViewModel: SubscriptionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return subscriptionView.setContentView(requireActivity(), container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscriptionViewModel.observeSubscriptionLiveData(
            viewLifecycleOwner,
            SubscriptionObserver(subscriptionView),
            tabName
        )
        subscriptionView.setAdapterListener(SubPlanSelectionListenerImpl(subscriptionViewModel))
        subscriptionView.setUpViewPager(this)
        subscriptionView.registerAutoScroll(viewLifecycleOwner.lifecycleScope)
        subscriptionView.privacyClickListener(PrivacyClickListenerImpl(subscriptionViewModel))
        subscriptionView.termsClickListener(TermsClickListenerImpl(subscriptionViewModel))
    }
}