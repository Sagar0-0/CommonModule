package fit.asta.health.old_subscription

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fit.asta.health.R
import fit.asta.health.old_subscription.listner.ContinueClickListenerImpl
import fit.asta.health.old_subscription.listner.SubscriptionViewPagerListener
import fit.asta.health.old_subscription.viewmodel.SubscriptionViewModel
import kotlinx.android.synthetic.main.subscription_activity.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SubscriptionActivity : AppCompatActivity() {

    private val subscriptionPagerView: SubscriptionPagerView by inject()
    private val subscriptionViewModel: SubscriptionViewModel by viewModel()

    companion object {

        fun launch(context: Context) {

            val intent = Intent(context, SubscriptionActivity::class.java)
            intent.apply {
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(subscriptionPagerView.setContentView(this))
        subscriptionPagerView.setUpViewPager(
            this,
            SubscriptionViewPagerListener(subscriptionViewModel)
        )
        subscriptionPagerView.continueClickListener(ContinueClickListenerImpl(subscriptionViewModel))
        subscriptionViewModel.fetchSubscriptionPlans()

        subscriptionViewModel.observeSubscriptionHeaderLiveData(
            this,
            SubscriptionPagerObserver(subscriptionPagerView)
        )

        tlbSubscription.title = getString(R.string.subscription)
        tlbSubscription.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}