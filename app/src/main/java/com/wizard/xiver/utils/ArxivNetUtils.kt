package com.wizard.xiver.utils

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.wizard.xiver.Feed
import com.wizard.xiver.utils.ArxivNetUtils.XmlUtil.xml
import kotlinx.serialization.StringFormat
import nl.adaptivity.xmlutil.serialization.XML
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class ArxivNetUtils {
    object RetrofitClient {
        private val contentType: MediaType = MediaType.get("application/xml")

        private val okHttpClient: OkHttpClient = OkHttpClient()
            .newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(RequestInterceptor)
            .build()


        fun retrofit(): Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://export.arxiv.org/api/")
            .addConverterFactory(xml().asConverterFactory(contentType))
            .build()
    }

    object XmlUtil {
        fun xml(): StringFormat {
            return XML
        }
    }

    object RequestInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val request = chain.request()
            Log.d("RequestInterceptor", "Outgoing request to ${request.url()}")
            return chain.proceed(request)
        }
    }

    interface ArxivInterface {
        @GET("query")
        suspend fun getSearchQueryItems(@Query("search_query") query :String , @Query("start") indexBegin: Int, @Query("max_results") indexEnd: Int = 10, @Query("sortBy") sortBy: String, @Query("sortOrder") sortOrder: String): Feed
    }

    object ArxivApi {
        val retrofitService: ArxivInterface by lazy {
            RetrofitClient.retrofit().create(ArxivInterface::class.java)
        }
    }
}
