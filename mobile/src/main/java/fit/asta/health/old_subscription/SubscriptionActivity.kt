package fit.asta.health.old_subscription

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import fit.asta.health.R
import fit.asta.health.old_subscription.listner.ContinueClickListenerImpl
import fit.asta.health.old_subscription.listner.SubscriptionViewPagerListener
import fit.asta.health.old_subscription.viewmodel.SubscriptionViewModel
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

        val tlbSubscription = findViewById<MaterialToolbar>(R.id.tlbSubscription)
        tlbSubscription.title = getString(R.string.subscription)
        tlbSubscription.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}