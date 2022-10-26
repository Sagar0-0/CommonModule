package fit.asta.health.old_subscription

import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.billingclient.api.*
import com.google.android.material.tabs.TabLayoutMediator
import fit.asta.health.HealthCareApp
import fit.asta.health.R
import fit.asta.health.old_subscription.adapter.SubscriptionPagerAdapter
import fit.asta.health.old_subscription.data.SubscriptionData
import fit.asta.health.old_subscription.data.SubscriptionTabsData
import fit.asta.health.old_subscription.listner.SubscriptionViewPagerListener
import fit.asta.health.old_subscription.ui.SubscriptionFragment
import kotlinx.android.synthetic.main.subscription_activity.view.*


class SubscriptionPagerViewImpl : SubscriptionPagerView {

    private var rootView: View? = null
    private var skuDetails: SkuDetails? = null
    val BASIC_SKU = "basic_subscription"
    val PREMIUM_SKU = "premium_subscription"
    lateinit var mContext: Activity

    override fun setContentView(activity: Activity): View? {
        rootView = LayoutInflater.from(activity).inflate(
            R.layout.subscription_activity, null,
            false
        )
        initialiseInApp(activity)
        return rootView
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun setUpViewPager(
        fragmentActivity: SubscriptionActivity,
        listener: SubscriptionViewPagerListener
    ) {
        rootView?.let {

            val adapter = SubscriptionPagerAdapter(fragmentActivity)
            it.subscriptionViewPager.adapter = adapter
            it.subscriptionViewPager.registerOnPageChangeCallback(listener)
        }
    }

    override fun changeState(state: SubscriptionPagerView.State) {
        when (state) {
            is SubscriptionPagerView.State.LoadSubscription -> updateTabs(state.subscriptionData)
            is SubscriptionPagerView.State.Error -> showError(state.message)
            SubscriptionPagerView.State.Empty -> showEmpty()
        }
    }

    private fun setViewPageAdapter(subPlanData: SubscriptionData) {

        rootView?.let { view ->

            val tabDetails = mutableListOf<SubscriptionTabsData>()
            view.cardRecurringBill.text = subPlanData.title

            val adapter = (view.subscriptionViewPager.adapter as SubscriptionPagerAdapter)
            adapter.setSubscriptionPlansList(subPlanData.plans.map { plan ->
                tabDetails += SubscriptionTabsData(plan.title)
                SubscriptionFragment(plan.title)
            })

            TabLayoutMediator(view.subscriptionTabs, view.subscriptionViewPager) { tab, position ->
                tab.text = tabDetails[position].tabTitle
                view.subscriptionViewPager.setCurrentItem(tab.position, true)
            }.attach()

        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun showError(msg: String) {

    }

    private fun showEmpty() {

    }

    private fun updateTabs(subscriptionData: SubscriptionData) {
        setViewPageAdapter(subscriptionData)
        rootView?.let {
            it.btnContinueSubs.setOnClickListener {
                launchBillingFlow(mContext)
            }
        }
    }

    override fun continueClickListener(listener: View.OnClickListener) {
        rootView?.let {
            it.btnContinueSubs.setOnClickListener {
                listener.onClick(it)
            }
        }
    }

    private fun initialiseInApp(activity: Activity) {

        try {
            mContext = activity
            billingClient?.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    Log.e("BillingActivity", "responseCode ${billingResult.responseCode}")
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        querySkuDetails()
                        Log.e("BillingActivity", "responseCode ${billingResult.responseCode}")
                    }
                }

                override fun onBillingServiceDisconnected() {
                    Log.e("BillingActivity", "disConnected")
                    // Try to restart the connection on the next request to
                    // Google Play by calling the startConnection() method.
                    Toast.makeText(mContext, "disConnected", Toast.LENGTH_LONG).show()
                }
            })
        } catch (e: Exception) {
            Log.e("BillingActivity", " Error: ${e.message}")
        }
    }

    private var billingClient =
        HealthCareApp.mContext?.let {
            BillingClient.newBuilder(it)
                .setListener { p0, p1 ->
                    Log.e("BillingActivity", "po  " + p0.responseCode + "  p1: " + p1)
                }.enablePendingPurchases().build()
        }

    private fun launchBillingFlow(activity: Activity): Int {

        Log.e("TAG", "BASIC_SKU $ { } ")

        // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
        try {
            val flowParams = BillingFlowParams.newBuilder().setSkuDetails(skuDetails!!).build()
            val responseCode = billingClient?.launchBillingFlow(activity, flowParams)?.responseCode
            Log.e("TAG", "launchBillingFlow: BillingResponse $responseCode")
        } catch (e: Exception) {
            Log.e("TAG", "launchBillingFlow: error " + e.localizedMessage)
        }

        return 0
    }

    private fun querySkuDetails() {
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(listOf(BASIC_SKU, PREMIUM_SKU)).setType(BillingClient.SkuType.INAPP)
        billingClient?.querySkuDetailsAsync(params.build()) { responseCode, skuDetailsList ->
            //skuDetails = skuDetailsList
            skuDetailsList?.forEach { skuDetails = it }
            Log.e("BillingActivity", "skuDetails ${skuDetails}")
            Log.e(
                "BillingActivity",
                "querySkuDetailsAsync ${responseCode.responseCode}   skuDetailsList $skuDetailsList"
            )
        }
    }
}