package org.calber.k9test

import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

interface OmdbApi {

    @retrofit2.http.GET("/")
    fun filmByTitle(@retrofit2.http.Query("t") title: String,
                    @retrofit2.http.Query("p") page: Int,
                    @retrofit2.http.Query("apikey") key: String): Observable<Film>

    @retrofit2.http.GET("/")
    fun filmById(@retrofit2.http.Query("i") id: String,
                    @retrofit2.http.Query("p") page: Int,
                    @retrofit2.http.Query("apikey") key: String): Observable<Film>

    @retrofit2.http.GET("/")
    fun search(@retrofit2.http.Query("s") expression: String,
               @retrofit2.http.Query("p") page: Int,
               @retrofit2.http.Query("apikey") key: String): Observable<Film>


    companion object Factory {

        val baseurl = "http://www.omdbapi.com"
        fun create(): OmdbApi {

            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            val retrofit = retrofit2.Retrofit.Builder()
                .addCallAdapterFactory(retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory.create())
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor())
                        .build()
                )
                .baseUrl(baseurl)
                .build()

            return retrofit.create(OmdbApi::class.java)
        }

    }
}