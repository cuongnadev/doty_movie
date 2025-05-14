package cuong.dev.dotymovie.di

import cuong.dev.dotymovie.data.remote.AuthApi
import cuong.dev.dotymovie.data.remote.MovieApi
import cuong.dev.dotymovie.data.remote.MovieFavoriteApi
import cuong.dev.dotymovie.data.remote.SeatApi
import cuong.dev.dotymovie.data.remote.ShowtimeApi
import cuong.dev.dotymovie.data.remote.TheaterApi
import cuong.dev.dotymovie.data.remote.TicketApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://5f17-2402-800-6205-227b-5841-259e-b96c-c54d.ngrok-free.app/api/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTheaterApi(retrofit: Retrofit): TheaterApi {
        return retrofit.create(TheaterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideShowtimeApi(retrofit: Retrofit): ShowtimeApi {
        return retrofit.create(ShowtimeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSeatApi(retrofit: Retrofit): SeatApi {
        return retrofit.create(SeatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTicketApi(retrofit: Retrofit): TicketApi {
        return retrofit.create(TicketApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieFavorite(retrofit: Retrofit): MovieFavoriteApi {
        return retrofit.create(MovieFavoriteApi::class.java)
    }
}