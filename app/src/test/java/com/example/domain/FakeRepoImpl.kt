package com.example.domain

import androidx.lifecycle.MutableLiveData
import com.example.movies.data.model.*
import com.example.movies.domain.Repo
import com.example.movies.vo.Resource
import java.util.concurrent.TimeUnit

class FakeRepoImpl : Repo {

    private val movieOne = Movie(
        724989,
        "https://image.tmdb.org/t/p/w500/86L8wqGMDbwURPni2t7FQ0nDjsH.jpg",
        "https://image.tmdb.org/t/p/w500/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg",
        "Hard Kill",
        "he work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.",
        4.8F,
        "2115001",
        "2020-10-23",
        listOf<Genre>(Genre("action"), Genre("comedy"))
    )

    private val movieList = mutableListOf<Movie>(
        Movie(
            635302,
            "https://image.tmdb.org/t/p/w500/xoqr4dMbRJnzuhsWDF3XNHQwJ9x.jpg",
            "https://image.tmdb.org/t/p/w500/h8Rb9gBr48ODIwYUttZNYeMWeUU.jpg",
            "Demon Slayer: Kimetsu no Yaiba - The Movie: Mugen Train",
            "Tanjirō Kamado, joined with Inosuke Hashibira, a boy raised by boars who wears a boar's head, and Zenitsu Agatsuma, a scared boy who reveals his true power when he sleeps, boards the Infinity Train on a new mission with the Fire Hashira, Kyōjurō Rengoku, to defeat a demon who has been tormenting the people and killing the demon slayers who oppose it!",
            6.7F,
            "1986559",
            "2020-10-16",
            listOf<Genre>(Genre("action"), Genre("drama")),
            System.currentTimeMillis()
        ),
        Movie(
            531219,
            "https://image.tmdb.org/t/p/w500/8rIoyM6zYXJNjzGseT3MRusMPWl.jpg",
            "https://image.tmdb.org/t/p/w500//betExZlgK0l7CZ9CsCBVcwO1OjL.jpg",
            "Roald Dahl's The Witches",
            "In late 1967, a young orphaned boy goes to live with his loving grandma in the rural Alabama town of Demopolis. As the boy and his grandmother encounter some deceptively glamorous but thoroughly diabolical witches, she wisely whisks him away to a seaside resort. Regrettably, they arrive at precisely the same time that the world's Grand High Witch has gathered.",
            7.1F,
            " 1718028",
            "2020-10-26",
            listOf<Genre>(Genre("horror"), Genre("drama")),
            System.currentTimeMillis()
        )
    )

    private val reviewList = mutableListOf<Review>(
        Review(
            "5fa3d112b2e074003e28f36a",
            "Peter89Spencer",
            "This was truly fantastic! You'd expect Disney/Pixar to make this film, but Netflix beat em to it! And Ken Jeong voicing Gobi; hilarious! He was like the new Olaf. I truly enjoyed this film."
        ),
        Review(
            "5fa3d112b2e074003e28f39a",
            "Alice",
            "Lovely story, very enjoyable"
        )
    )

    private val reviewOne = Review(
        "531219",
        "Carl",
        "Good movie"
    )

    private val savedMovieList = mutableListOf<MovieEntity>(
        MovieEntity(
            635302,
            "https://image.tmdb.org/t/p/w500/xoqr4dMbRJnzuhsWDF3XNHQwJ9x.jpg",
            "https://image.tmdb.org/t/p/w500/h8Rb9gBr48ODIwYUttZNYeMWeUU.jpg",
            "Demon Slayer: Kimetsu no Yaiba - The Movie: Mugen Train",
            "Tanjirō Kamado, joined with Inosuke Hashibira, a boy raised by boars who wears a boar's head, and Zenitsu Agatsuma, a scared boy who reveals his true power when he sleeps, boards the Infinity Train on a new mission with the Fire Hashira, Kyōjurō Rengoku, to defeat a demon who has been tormenting the people and killing the demon slayers who oppose it!",
            6.7F,
            "1986559",
            "2020-10-16",
            listOf<Genre>(Genre("action"), Genre("drama")),
            System.currentTimeMillis()
        ),
        MovieEntity(
            531219,
            "https://image.tmdb.org/t/p/w500/8rIoyM6zYXJNjzGseT3MRusMPWl.jpg",
            "https://image.tmdb.org/t/p/w500//betExZlgK0l7CZ9CsCBVcwO1OjL.jpg",
            "Roald Dahl's The Witches",
            "In late 1967, a young orphaned boy goes to live with his loving grandma in the rural Alabama town of Demopolis. As the boy and his grandmother encounter some deceptively glamorous but thoroughly diabolical witches, she wisely whisks him away to a seaside resort. Regrettably, they arrive at precisely the same time that the world's Grand High Witch has gathered.",
            7.1F,
            " 1718028",
            "2020-10-26",
            listOf<Genre>(Genre("horror"), Genre("drama")),
            System.currentTimeMillis()
        )
    )

    private val savedReviewList = mutableListOf<ReviewEntity>(
        ReviewEntity(
            "5fa3d112b2e074003e28f39a",
            531219,
            "Peter89Spencer",
            "This was truly fantastic! You'd expect Disney/Pixar to make this film, but Netflix beat em to it! And Ken Jeong voicing Gobi; hilarious! He was like the new Olaf. I truly enjoyed this film."
        ),
        ReviewEntity(
            "5fa3d112b2e074003e28f32a",
            531219,
            "Alice",
            "Lovely story, very enjoyable"
        )
    )

    private val observableMoviesList = MutableLiveData<List<Movie>>(movieList)

    private val shouldReturnNetworkError = false

    override suspend fun getMoviesList(): Resource<List<Movie>> {
        return if (shouldReturnNetworkError) {
            Resource.Failure(Exception("Error"))
        } else {
            Resource.Success(movieList)
        }
    }

    override suspend fun getMovieDetail(movieId: Int): Resource<Movie> {
        return if (shouldReturnNetworkError) {
            Resource.Failure(Exception("Error"))
        } else {
            Resource.Success(movieList.find { movie -> movie.id == movieId }!!)
        }
    }

    override suspend fun getReviewsList(movieId: Int): Resource<List<Review>> {
        return if (shouldReturnNetworkError) {
            Resource.Failure(Exception("Error"))
        } else {
            Resource.Success(reviewList)
        }
    }

    override suspend fun getSavedMoviesList(): Resource<List<MovieEntity>> {
        return if (shouldReturnNetworkError) {
            Resource.Failure(Exception("Error"))
        } else {
            Resource.Success(savedMovieList)
        }
    }

    override suspend fun getSavedMovieDetail(movieId: Int): Resource<MovieEntity> {
        return if (shouldReturnNetworkError) {
            Resource.Failure(Exception("Error"))
        } else {
            Resource.Success(savedMovieList.find { movie -> movie.id == movieId }!!)
        }
    }

    override suspend fun getSavedReviewsList(movieId: Int): Resource<List<ReviewEntity>> {
        return if (shouldReturnNetworkError) {
            Resource.Failure(Exception("Error"))
        } else {
            Resource.Success(savedReviewList.filter { review -> review.movieId == movieId })
        }
    }

    override suspend fun insertMovie(movie: MovieEntity) {
        movieList.add(
            Movie(
                movie.id,
                movie.backdropPath,
                movie.imagePath,
                movie.name,
                movie.description,
                movie.rate,
                movie.popularity,
                movie.releaseDate,
                movie.genres
            )
        )
    }

    override suspend fun insertMovieDetail(movie: MovieEntity) {
        movieList.add(
            Movie(
                movie.id,
                movie.backdropPath,
                movie.imagePath,
                movie.name,
                movie.description,
                movie.rate,
                movie.popularity,
                movie.releaseDate,
                movie.genres
            )
        )
    }

    override suspend fun insertReviews(reviewsList: List<ReviewEntity>) {
        for (review in reviewsList) {
            reviewList.add(Review(review.id, review.reviewer, review.reviewContent))
        }
    }

    override suspend fun deleteMovie() {
        val expireTime = TimeUnit.HOURS.toMillis(24)
        for (movie in movieList) {
            if (movie.createdTime < System.currentTimeMillis() - expireTime) {
                movieList.remove(movie)
            }
        }
    }
}