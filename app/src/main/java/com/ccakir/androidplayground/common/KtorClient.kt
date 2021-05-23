package com.ccakir.androidplayground.common

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*

object KtorClient {

    private const val TAG = "KtorClient"
    private const val TIME_OUT = 60_000

    val client = HttpClient(Android) {

        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                isLenient = true
            })

            engine {
                connectTimeout = TIME_OUT
                socketTimeout = TIME_OUT
            }
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
            host = "api.github.com/users"
            url.protocol = URLProtocol.HTTPS
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
}