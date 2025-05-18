package cuong.dev.dotymovie.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cuong.dev.dotymovie.model.movie.Movie
import cuong.dev.dotymovie.model.movie.MovieFavoriteDTO
import cuong.dev.dotymovie.model.movie.UpdateMovieDTO
import cuong.dev.dotymovie.repository.MovieFavoriteRepository
import cuong.dev.dotymovie.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieFavoriteRepository: MovieFavoriteRepository
): ViewModel() {
    private val _movie: MutableState<Movie?> = mutableStateOf(null)
    val movie: State<Movie?> = _movie

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: Flow<List<Movie>> = _movies.asStateFlow()

    private val _movieHighlights = MutableStateFlow<List<Movie>>(emptyList())
    val movieHighlights: Flow<List<Movie>> = _movieHighlights.asStateFlow()

    private val _newMovies = MutableStateFlow<List<Movie>>(emptyList())
    val newMovies: Flow<List<Movie>> = _newMovies.asStateFlow()

    private val _comingSoonMovies = MutableStateFlow<List<Movie>>(emptyList())
    val comingSoonMovies: Flow<List<Movie>> = _comingSoonMovies.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    suspend fun fetchMovieHighLights() {
        _isLoading.value = true

        try {
            val response = movieRepository.getMovieHighLights()

            if(response.isSuccessful) {
                response.body()?.let {
                    _movieHighlights.value = it
                } ?: run {
                    _movieHighlights.value = emptyList()
                }
            } else {
                _movieHighlights.value = emptyList()
                Log.e("MovieViewModel", "Fetch movie highlights failed: ${response.code()}")
            }
        } catch (e: Exception) {
            _movieHighlights.value = emptyList()
            Log.e("MovieViewModel", "Exception during fetching movie highlights", e)
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun fetchNewMovies() {
        _isLoading.value = true

        try {
            val response = movieRepository.getNewMovies()

            if(response.isSuccessful) {
                response.body()?.let {
                    _newMovies.value = it
                } ?: {
                    _newMovies.value = emptyList()
                }
            } else {
                _newMovies.value = emptyList()
                Log.e("MovieViewModel", "Fetch new movies failed: ${response.code()}")
            }
        } catch (e: Exception) {
            _newMovies.value = emptyList()
            Log.e("MovieViewModel", "Exception during fetching new movies", e)
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun fetchComingSoonMovies() {
        _isLoading.value = true

        try {
            val response = movieRepository.getComingSoonMovies()

            if (response.isSuccessful) {
                response.body()?.let {
                    _comingSoonMovies.value = it
                } ?: {
                    _comingSoonMovies.value = emptyList()
                }
            } else {
                _comingSoonMovies.value = emptyList()
                Log.e("MovieViewModel", "Fetch coming soon movies failed: ${response.code()}")
            }
        } catch (e: Exception) {
            _comingSoonMovies.value = emptyList()
            Log.e("MovieViewModel", "Exception during fetching coming soon movies", e)
        } finally {
            _isLoading.value = false
        }
    }

    suspend fun fetchMovieById(movieId: Int, userId: Int) {
        try {
            val response = movieRepository.getMovieById(movieId, userId)

            if(response.isSuccessful) {
                _movie.value = response.body()
            } else {
                _movie.value = null
            }
        } catch (e: Exception) {
            _movie.value = null
            Log.e("MovieViewModel", "Exception during fetching movie by id: $movieId", e)
        }
    }

    suspend fun fetchMovieFavorite(userId: Int) {
        _isLoading.value = true
        try {
            val response = movieFavoriteRepository.getMovieFavorites(userId)

            if (response.isSuccessful) {
                response.body()?.let {
                    val movies = it.map { movieFavorite -> movieFavorite.movie }
                    _movies.value = movies
                    Log.d("MovieViewModel", "Movies fetched: $it")
                } ?: run {
                    _movies.value = emptyList()
                }
            } else {
                _movies.value = emptyList()
                Log.e("MovieViewModel", "Fetching data movie favorite failed: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("MovieViewModel", "Exception during fetching movie favorite", e)
        } finally {
            _isLoading.value = false
        }
    }

    fun setMovie(movie: Movie?) {
        _movie.value = movie
    }

    suspend fun update(movieId: Int, updateDTO: UpdateMovieDTO) {
        try {
            val response = movieRepository.update(movieId, updateDTO)

            if (response.isSuccessful) {
                _movie.value = _movie.value?.applyUpdate(updateDTO)
                Log.i("MovieViewModel", "Update movie by movieId($movieId) successful!")
            } else {
                Log.e("MovieViewModel", "Update movie by movieId($movieId) failed: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("MovieViewModel", "Exception during update movie by id: $movieId", e)
        }
    }

    suspend fun setFavorite(isFavorite: Boolean, request: MovieFavoriteDTO) {
        try {
            if (isFavorite) {
                val response = movieFavoriteRepository.createMovieFavorite(request)
                if (response.isSuccessful) {
                    Log.i("MovieViewModel", "Set favorite movie by movieId(${request.movieId}) successful!")
                } else {
                    Log.e("MovieViewModel", "Set favorite movie by movieId(${request.movieId}) failed: ${response.code()}")
                }
            } else {
                val response = movieFavoriteRepository.deleteMovieFavorite(request)
                if (response.isSuccessful) {
                    Log.i("MovieViewModel", "Cancel favorite movie by movieId(${request.movieId}) successful!")
                } else {
                    Log.e("MovieViewModel", "Cancel favorite movie by movieId(${request.movieId}) failed: ${response.code()}")
                }
            }
        } catch (e: Exception) {
            Log.e("MovieViewModel", "Exception during set favorite movie", e)
        }
    }

    suspend fun searchMovie(query: String) {
        _isLoading.value = true
        _movies.emit(emptyList())

        try {
            val response = movieRepository.searchMovies(query)

            if (response.isSuccessful) {
                response.body()?.let {
                    _movies.emit(it)
                }
            }
        } catch (e: Exception) {
            Log.e("MovieViewModel", "Exception during search movie with q={$query}", e)
        } finally {
            _isLoading.value = false
        }
    }
}