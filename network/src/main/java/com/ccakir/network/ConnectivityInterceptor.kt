package com.ccakir.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor(private val wifiService: WifiService) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (wifiService.isOnline()) {
            return chain.proceed(chain.request())
        } else
            throw NoConnectivityException()
    }
}

class NoConnectivityException : IOException() {
    override val message: String
        get() = "No network available, please check your WiFi or Data connection"
}