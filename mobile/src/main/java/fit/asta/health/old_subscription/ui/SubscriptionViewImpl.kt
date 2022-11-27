package fit.asta.health.old_subscription.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import fit.asta.health.R
import fit.asta.health.common.carousel.CarouselViewPagerAdapter
import fit.asta.health.common.carousel.autoScroll
import fit.asta.health.old_subscription.adapter.SubscriptionAdapter
import fit.asta.health.old_subscription.data.CarouselData
import fit.asta.health.old_subscription.data.SubscriptionPlanData
import fit.asta.health.old_subscription.listner.SubPlanSelectionListener


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

            val rcvSubscriptionPayment =
                it.findViewById<RecyclerView>(R.id.rcv_subscription_payment)
            rcvSubscriptionPayment.layoutManager =
                LinearLayoutManager(it.context, LinearLayoutManager.HORIZONTAL, false)
            rcvSubscriptionPayment.adapter = SubscriptionAdapter()
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
            (it.findViewById<RecyclerView>(R.id.rcv_subscription_payment).adapter as SubscriptionAdapter).setListener(
                listener
            )
        }
    }

    private fun setAdapter(subPlanData: SubscriptionPlanData) {
        rootView?.let {

            val sliderViewPager = it.findViewById<ViewPager2>(R.id.sliderViewPager)
            (sliderViewPager.adapter as CarouselViewPagerAdapter).setCarouselList(
                subPlanData.features.map {
                    CarouselFragment.newInstance(CarouselData().apply {
                        title = it.title
                        description = it.description
                        url = it.url
                    })
                })

            (it.findViewById<RecyclerView>(R.id.rcv_subscription_payment).adapter as SubscriptionAdapter).updateList(
                subPlanData.subscriptions
            )
            it.findViewById<TextView>(R.id.termsInfo).text = subPlanData.desc
            TabLayoutMediator(
                it.findViewById(R.id.tabIndicator),
                sliderViewPager
            ) { _, _ -> }.attach()
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun showError(msg: String) {

    }

    private fun showEmpty() {

    }

    override fun setUpViewPager(fragment: Fragment) {
        rootView?.let {

            it.findViewById<ViewPager2>(R.id.sliderViewPager).adapter =
                CarouselViewPagerAdapter(fragment)
        }
    }

    override fun registerAutoScroll(lifecycleScope: LifecycleCoroutineScope) {

        rootView?.let {
            it.findViewById<ViewPager2>(R.id.sliderViewPager).autoScroll(
                lifecycleScope = lifecycleScope,
                interval = CarouselViewPagerAdapter.REFRESH_RATE
            )
        }
    }

    override fun privacyClickListener(listener: View.OnClickListener) {
        rootView?.let {
            it.findViewById<AppCompatTextView>(R.id.privacyPolicy).setOnClickListener {
                listener.onClick(it)
            }
        }
    }

    override fun termsClickListener(listener: View.OnClickListener) {
        rootView?.let {
            it.findViewById<AppCompatTextView>(R.id.termsOfService).setOnClickListener {
                listener.onClick(it)
            }
        }
    }
}