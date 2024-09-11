package com.example.coop.di

import android.content.Context
import com.example.coop.api.ApiService
import com.example.coop.iteractor.LoginRepo
import com.example.coop.iteractor.LoginRepoImpl
import com.example.coop.utils.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        explicitNulls = true
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        @ApplicationContext context: Context,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    fun provideApi(
        client: OkHttpClient,
        networkJson: Json,
    ): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(ApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideLoginRepo(
        apiService: ApiService,
    ):LoginRepo{
        return LoginRepoImpl(apiService)
    }
}