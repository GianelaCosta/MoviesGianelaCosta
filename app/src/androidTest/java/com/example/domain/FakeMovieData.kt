package com.example.domain

import com.example.movies.data.model.Genre
import com.example.movies.data.model.Movie
import com.example.movies.data.model.Review

object FakeMovieData {

    val movieList = listOf<Movie>(
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

    val reviewList = listOf<Review>(
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
}