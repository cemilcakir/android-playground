package com.ccakir.androidplayground.common.network

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KtorClient @Inject constructor(private val connectivityInterceptor: ConnectivityInterceptor) {

    companion object {
        private const val TAG = "KtorClient"
    }

    val client = HttpClient(OkHttp) {

        engine {
            addInterceptor(connectivityInterceptor)
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                isLenient = true
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d(TAG, message)
                }
            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d(TAG, "${response.status.value}")
            }
        }

        install(DefaultRequest) {
            host = "api.github.com"
            url.protocol = URLProtocol.HTTPS
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
}