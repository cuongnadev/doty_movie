package cuong.dev.dotymovie.di

import cuong.dev.dotymovie.data.remote.AuthApi
import cuong.dev.dotymovie.data.remote.MovieApi
import cuong.dev.dotymovie.data.remote.MovieFavoriteApi
import cuong.dev.dotymovie.data.remote.SeatApi
import cuong.dev.dotymovie.data.remote.ShowtimeApi
import cuong.dev.dotymovie.data.remote.TheaterApi
import cuong.dev.dotymovie.data.remote.TicketApi
import cuong.dev.dotymovie.data.remote.UserApi
import cuong.dev.dotymovie.repository.AuthRepository
import cuong.dev.dotymovie.repository.MovieFavoriteRepository
import cuong.dev.dotymovie.repository.MovieRepository
import cuong.dev.dotymovie.repository.SeatRepository
import cuong.dev.dotymovie.repository.ShowtimeRepository
import cuong.dev.dotymovie.repository.TheaterRepository
import cuong.dev.dotymovie.repository.TicketRepository
import cuong.dev.dotymovie.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AuthApi): AuthRepository {
        return AuthRepository(authApi)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(movieApi: MovieApi): MovieRepository {
        return MovieRepository(movieApi)
    }

    @Provides
    @Singleton
    fun provideTheaterRepository(theaterApi: TheaterApi): TheaterRepository {
        return TheaterRepository(theaterApi)
    }

    @Provides
    @Singleton
    fun provideShowtimeRepository(showtimeApi: ShowtimeApi): ShowtimeRepository {
        return ShowtimeRepository(showtimeApi)
    }

    @Provides
    @Singleton
    fun provideSeatRepository(seatApi: SeatApi): SeatRepository {
        return SeatRepository(seatApi)
    }

    @Provides
    @Singleton
    fun provideTicketRepository(ticketApi: TicketApi): TicketRepository {
        return TicketRepository(ticketApi)
    }

    @Provides
    @Singleton
    fun provideMovieFavoriteRepository(movieFavoriteApi: MovieFavoriteApi): MovieFavoriteRepository {
        return MovieFavoriteRepository(movieFavoriteApi)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userApi: UserApi): UserRepository {
        return UserRepository(userApi)
    }
}