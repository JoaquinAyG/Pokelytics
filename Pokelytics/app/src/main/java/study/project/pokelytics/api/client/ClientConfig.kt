package study.project.pokelytics.api.client

import java.util.concurrent.TimeUnit
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import study.project.pokelytics.api.ApiConstants

class ClientConfig(
    val rootUrl: HttpUrl = HttpUrl.parse(ApiConstants.BASE_URL)!!,
    val okHttpConfig: OkHttpClient.Builder.() -> OkHttpClient.Builder = {
        retryOnConnectionFailure(false)
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
    }
)
