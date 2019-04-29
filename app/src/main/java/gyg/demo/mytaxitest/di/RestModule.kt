package gyg.demo.mytaxitest.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import gyg.demo.mytaxitest.BuildConfig
import gyg.demo.mytaxitest.IdlingResources
import gyg.demo.mytaxitest.data.TaxiService
import gyg.demo.mytaxitest.data.model.TaxiType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RestModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(TaxiType::class.java, CustomSerializerDeserializer())
        return builder.create()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Singleton
    @Provides
    fun provideRxJavaAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofitClient(
        client: OkHttpClient,
        converter: GsonConverterFactory,
        rxAdapter: RxJava2CallAdapterFactory
    ): Retrofit {

        if (BuildConfig.DEBUG) {
            IdlingResources.registerOkHttp(client)
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(converter)
            .addCallAdapterFactory(rxAdapter)
            .build()
    }

    @Singleton
    @Provides
    fun provideTaxiListService(retrofit: Retrofit): TaxiService = retrofit.create(TaxiService::class.java)

    companion object {
        const val BASE_URL = "https://fake-poi-api.mytaxi.com/"
    }

}