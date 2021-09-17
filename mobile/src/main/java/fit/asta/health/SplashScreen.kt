package fit.asta.health

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import fit.asta.health.utils.NetworkConnectivity


class SplashScreen : AppCompatActivity() {

    private lateinit var networkConnectivity: NetworkConnectivity
    private var snackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerConnectivityReceiver()

        if (FirebaseAuth.getInstance().currentUser != null) {

            startMain()

        } else {

            startMain()
        }
    }

    private fun startMain() {

        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    private fun registerConnectivityReceiver() {

        snackBar = Snackbar.make(
            window.decorView.rootView,
            getString(R.string.OFFLINE_STATUS),
            Snackbar.LENGTH_INDEFINITE
        )

        networkConnectivity = NetworkConnectivity(this)
        networkConnectivity.observe(this, Observer { status ->

            showNetworkMessage(status)
        })
    }

    private fun showNetworkMessage(isConnected: Boolean) {

        if (!isConnected) {
            snackBar?.show()
        } else {
            snackBar?.dismiss()
        }
    }
}