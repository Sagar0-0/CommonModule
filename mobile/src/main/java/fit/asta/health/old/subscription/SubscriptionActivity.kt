package fit.asta.health.old.subscription

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import fit.asta.health.R
import fit.asta.health.old.subscription.listner.ContinueClickListenerImpl
import fit.asta.health.old.subscription.listner.SubscriptionViewPagerListener
import fit.asta.health.old.subscription.viewmodel.SubscriptionViewModel
import javax.inject.Inject


class SubscriptionActivity : AppCompatActivity() {

    @Inject
    lateinit var subscriptionPagerView: SubscriptionPagerView
    private val subscriptionViewModel: SubscriptionViewModel by viewModels()

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