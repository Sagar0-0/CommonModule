package fit.asta.health

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.SkuDetails
import com.android.billingclient.api.SkuDetailsParams


class BillingActivity : AppCompatActivity() {

    private var skuDetails: SkuDetails? = null
    val BASIC_SKU = "basic_subscription"
    val PREMIUM_SKU = "premium_subscription"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.billing_activity)

        try {
            billingClient?.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    Log.e("BillingActivity", "responseCode ${billingResult.responseCode}")
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        querySkuDetails()
                        Toast.makeText(
                            this@BillingActivity,
                            "responseCode ${billingResult.responseCode}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onBillingServiceDisconnected() {
                    Log.e("BillingActivity", "disConnected")
                    // Try to restart the connection on the next request to
                    // Google Play by calling the startConnection() method.
                    Toast.makeText(this@BillingActivity, "disConnected", Toast.LENGTH_LONG).show()
                }
            })
        } catch (e: Exception) {
            Log.e("BillingActivity", " ErrorMessage: ${e.message}")
        }

        findViewById<Button>(R.id.payBtn).setOnClickListener {
            queryPurchases()
            launchBillingFlow(this)
        }
    }

    fun launchBillingFlow(activity: Activity): Int {

        Log.e("TAG", "BASIC_SKU $ { } ")

        // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
        try {
            val flowParams = BillingFlowParams.newBuilder().setSkuDetails(skuDetails!!).build()
            val responseCode = billingClient?.launchBillingFlow(activity, flowParams)?.responseCode
            Log.e("TAG", "launchBillingFlow: BillingResponse $responseCode")
        } catch (e: Exception) {
            Log.e("TAG", "launchBillingFlow: error " + e.message)
        }

        return 0
    }

    private val purchasesUpdateListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            try {
                Log.e(
                    "TAG",
                    "purchasesUpdateListener: error " + billingResult.responseCode + " purchases: " + purchases?.get(
                        0
                    )
                )
            } catch (e: Exception) {
            }
        }

    private var billingClient =
        HealthCareApp.mContext?.let {
            BillingClient.newBuilder(it)
                .setListener { p0, p1 ->
                    Log.e(
                        "BillingActivity",
                        "po  " + p0.responseCode + "  p1: " + p1
                    )
                }.enablePendingPurchases().build()
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

    private fun queryPurchases() {

        billingClient!!.queryPurchasesAsync(
            BillingClient.SkuType.SUBS
        ) { _, list -> processPurchases(list) }
    }

    private fun processPurchases(purchasesList: List<Purchase>?) {
        Log.e("TAG", "processPurchases: purchase list $purchasesList")
    }
}