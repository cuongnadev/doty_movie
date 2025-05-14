package cuong.dev.dotymovie.data.remote

interface ApiService {
    val authApi: AuthApi
    val movieApi: MovieApi
}