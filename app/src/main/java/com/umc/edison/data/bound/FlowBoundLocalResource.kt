package com.umc.edison.data.bound

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.umc.edison.domain.DataResource
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector

class FlowBoundLocalResource<DomainType, DataType>(
    localDataAction: suspend () -> DataType,
    private val getNotSyncedAction: suspend () -> List<DataType>,
    private val syncRemoteAction: suspend (List<DataType>) -> Unit,
    private val updateSyncedAction: suspend (List<DataType>) -> Unit,
) : FLowBaseBoundResource<DomainType, DataType>(localDataAction) {

    @InternalCoroutinesApi
    override suspend fun collect(collector: FlowCollector<DataResource<DomainType>>) {
        try {
            fetchFromSource(collector)

//            val notSyncedData = getNotSyncedAction()
//            if (notSyncedData.isNotEmpty() && isWifiConnected()) {
//                syncRemoteAction(notSyncedData)
//                updateSyncedAction(notSyncedData)
//            }
        } catch (e: Exception) {
            Log.e("FlowBoundLocalResource", "sync data: $e")
        }
    }

//    private fun isWifiConnected(): Boolean {
//        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val network = connectivityManager.activeNetwork ?: return false
//        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
//        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
//    }
}
