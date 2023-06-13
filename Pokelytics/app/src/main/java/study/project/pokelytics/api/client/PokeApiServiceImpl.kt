package study.project.pokelytics.api.client

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import study.project.pokelytics.api.util.ApiResourceAdapter
import study.project.pokelytics.api.util.NamedApiResourceAdapter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import study.project.pokelytics.api.model.ApiResource
import study.project.pokelytics.api.model.NamedApiResource

internal class PokeApiServiceImpl(
    private val config: ClientConfig
) : PokeApiService by Retrofit.Builder()
    .baseUrl(config.rootUrl)
    .addConverterFactory(
        GsonConverterFactory.create(
            GsonBuilder().apply {
                setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                registerTypeAdapter(
                    TypeToken.get(ApiResource::class.java).type,
                    ApiResourceAdapter()
                )
                registerTypeAdapter(
                    TypeToken.get(NamedApiResource::class.java).type,
                    NamedApiResourceAdapter()
                )
            }.create()
        )
    )
    .client(OkHttpClient.Builder().(config.okHttpConfig)().build())
    .build()
    .create(PokeApiService::class.java)
