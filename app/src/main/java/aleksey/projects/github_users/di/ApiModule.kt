package aleksey.projects.github_users.di

import aleksey.projects.github_users.data.AppPrefs
import aleksey.projects.github_users.data.api.VkApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession


private const val VK_API_URL = "https://api.vk.com/"
private const val KEY_HEADER_EMPLOYEE_ID = "access_token"


val apiModule = module {

    factory { createOkHttpClient(get(), get()) }

    single { createApiService<VkApi>(get(), VK_API_URL) }

    /*single { createApiService<ClientsApi>(get(), CLIENTS_API_MOCK_URL) }*/

    factory { createLoggingInterceptor() }

    factory { createNetworkInterceptor(get()) }

}

fun createLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
        Timber.d(message)
    })
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

fun createNetworkInterceptor(appPrefs: AppPrefs): Interceptor {
    return Interceptor {
        val requestBuilder = it.request().newBuilder()
        val request = requestBuilder
            //.addHeader(KEY_HEADER_EMPLOYEE_ID, appPrefs.getVkAccessToken())
            .build()
        it.proceed(request)
    }
}

fun createOkHttpClient(networkInterceptor: Interceptor, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(30L, TimeUnit.SECONDS)
        .addInterceptor(networkInterceptor)
        .addInterceptor(loggingInterceptor)
        .hostnameVerifier(object : HostnameVerifier {
            override fun verify(hostname: String?, session: SSLSession?): Boolean {
                return true
            }
        })
        .retryOnConnectionFailure(false)
        .build()
}

inline fun <reified T> createApiService(okHttpClient: OkHttpClient, apiUrl: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(apiUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}