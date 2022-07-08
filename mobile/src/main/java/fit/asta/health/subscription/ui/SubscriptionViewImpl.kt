package fit.asta.health.subscription.ui

import android.app.Activity
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import fit.asta.health.R
import fit.asta.health.common.carousel.CarouselViewPagerAdapter
import fit.asta.health.common.carousel.autoScroll
import fit.asta.health.subscription.adapter.SubscriptionAdapter
import fit.asta.health.subscription.data.CarouselData
import fit.asta.health.subscription.data.SubscriptionPlanData
import fit.asta.health.subscription.listner.SubPlanSelectionListener
import kotlinx.android.synthetic.main.subscription_page.view.*


class SubscriptionViewImpl : SubscriptionView {

    private var rootView: View? = null

    override fun setContentView(activity: Activity, container: ViewGroup?): View? {
        rootView = LayoutInflater.from(activity).inflate(
            R.layout.subscription_page, container,
            false
        )
        initializeViews()
        return rootView
    }

    private fun initializeViews() {
        rootView?.let {

            it.rcv_subscription_payment.layoutManager =
                LinearLayoutManager(it.context, LinearLayoutManager.HORIZONTAL, false)
            it.rcv_subscription_payment.adapter = SubscriptionAdapter()
        }
    }

    override fun changeState(state: SubscriptionView.State) {
        when (state) {
            is SubscriptionView.State.LoadSubscriptionPlan -> {
                setAdapter(state.subPlanData)
            }
            is SubscriptionView.State.Error -> showError(state.message)
            SubscriptionView.State.Empty -> showEmpty()
        }
    }

    override fun setAdapterListener(listener: SubPlanSelectionListener) {
        rootView?.let {
            (it.rcv_subscription_payment.adapter as SubscriptionAdapter).setListener(listener)
        }
    }

    private fun setAdapter(subPlanData: SubscriptionPlanData) {
        rootView?.let {

            (it.sliderViewPager.adapter as CarouselViewPagerAdapter).setCarouselList(
                subPlanData.features.map {
                    CarouselFragment.newInstance(CarouselData().apply {
                        title = it.title
                        description = it.description
                        url = it.url
                    })
                })

            (it.rcv_subscription_payment.adapter as SubscriptionAdapter).updateList(subPlanData.subscriptions)
            it.termsInfo.text = subPlanData.desc
            TabLayoutMediator(it.tabIndicator, it.sliderViewPager) { _, _ -> }.attach()
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun showError(msg: String) {

    }

    private fun showEmpty() {

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun setUpViewPager(fragment: Fragment) {
        rootView?.let {

            it.sliderViewPager.adapter = CarouselViewPagerAdapter(fragment)
        }
    }

    override fun registerAutoScroll(lifecycleScope: LifecycleCoroutineScope) {

        rootView?.let {
            it.sliderViewPager.autoScroll(
                lifecycleScope = lifecycleScope,
                interval = CarouselViewPagerAdapter.REFRESH_RATE
            )
        }
    }

    override fun privacyClickListener(listener: View.OnClickListener) {
        rootView?.let {
            it.privacyPolicy.setOnClickListener {
                listener.onClick(it)
            }
        }
    }

    override fun termsClickListener(listener: View.OnClickListener) {
        rootView?.let {
            it.termsOfService.setOnClickListener {
                listener.onClick(it)
            }
        }
    }
}