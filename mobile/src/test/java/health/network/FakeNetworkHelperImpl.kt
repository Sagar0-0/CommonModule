package health.network

import fit.asta.health.network.NetworkHelper

class FakeNetworkHelperImpl : NetworkHelper {
    override fun isConnected(): Boolean {
        return true
    }
}