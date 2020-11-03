 package com.example.movies.ui

 import android.os.Bundle
 import android.view.LayoutInflater
 import android.view.View
 import android.view.ViewGroup
 import android.widget.Toast
 import androidx.fragment.app.Fragment
 import androidx.fragment.app.viewModels
 import androidx.lifecycle.Observer
 import androidx.navigation.fragment.findNavController
 import androidx.recyclerview.widget.GridLayoutManager
 import androidx.recyclerview.widget.RecyclerView
 import com.example.movies.AppDatabase
 import com.example.movies.R
 import com.example.movies.data.DataSource
 import com.example.movies.data.model.Movie
 import com.example.movies.data.model.MovieEntity
 import com.example.movies.domain.RepoImpl
 import com.example.movies.ui.MainAdapter.OnMovieClickListener
 import com.example.movies.ui.viewModel.MainViewModel
 import com.example.movies.ui.viewModel.VMFactory
 import com.example.movies.vo.Resource
import kotlinx.android.synthetic.main.fragment_main.*

 class MainFragment : Fragment(), OnMovieClickListener {

     private val viewModel by viewModels<MainViewModel> {
         VMFactory(
             RepoImpl(
                 DataSource(
                     AppDatabase.getDatabase(
                         requireActivity().applicationContext
                     )
                 )
             )
         )
     }

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
     }

     override fun onCreateView(
         inflater: LayoutInflater, container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         return inflater.inflate(R.layout.fragment_main, container, false)
     }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setupRecyclerView()
//        viewModel.fetchMoviesList.observe(viewLifecycleOwner, Observer { result->
//            when(result){
//                is Resource.Loading -> {
//                    progressBar.visibility = View.VISIBLE
//                }
//                is Resource.Success -> {
//                    progressBar.visibility = View.GONE
//                    downloadMoviesList(result.data)
//                    rv_movies.adapter = MainAdapter(requireContext(), result.data, this)
//                }
//                is Resource.Failure -> {
//                    progressBar.visibility = View.GONE
//                    Toast.makeText(requireContext(), "An error occurred while loading data ${result.exception}", Toast.LENGTH_LONG).show()
//                }
//            }
//        })
//    }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
         setupRecyclerView()
         // Fetch from server
         viewModel.fetchMoviesList.observe(viewLifecycleOwner, Observer { result ->
             when (result) {
                 is Resource.Loading -> {
                     progressBar.visibility = View.VISIBLE
                 }
                 is Resource.Success -> {
                     progressBar.visibility = View.GONE
                     downloadMoviesList(result.data)
                 }
                 is Resource.Failure -> {
                     progressBar.visibility = View.GONE
                     Toast.makeText(
                         requireContext(),
                         "An error occurred while loading data ${result.exception}",
                         Toast.LENGTH_LONG
                     ).show()
                 }
             }
         })
         // display room content
         viewModel.fetchDownloadedMoviesList().observe(viewLifecycleOwner, Observer { result ->
             when (result) {
                 is Resource.Loading -> {
                     progressBar.visibility = View.VISIBLE
                 }
                 is Resource.Success -> {
                     progressBar.visibility = View.GONE
                     val list = result.data.map {
                         Movie(
                             it.id,
                             it.backdropPath,
                             it.imagePath,
                             it.name,
                             it.description,
                             it.rate,
                             it.popularity,
                             it.releaseDate
                         )
                     }
                     rv_movies.adapter = MainAdapter(requireContext(), list, this)
                 }
                 is Resource.Failure -> {
                     progressBar.visibility = View.GONE
                     Toast.makeText(
                         requireContext(),
                         "An error occurred while loading data ${result.exception}",
                         Toast.LENGTH_LONG
                     ).show()
                 }
             }
         })
     }


     override fun onMovieClick(movie: Movie) {
         val bundle = Bundle()
         bundle.putParcelable("movie", movie)
         findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
     }

     private fun setupRecyclerView() {
         rv_movies.setHasFixedSize(true)
         rv_movies.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
     }

     private fun downloadMoviesList(moviesList: List<Movie>) {
         for (movie in moviesList) {
             viewModel.saveMovie(
                 MovieEntity(
                     movie.id,
                     movie.backdropPath,
                     movie.imagePath,
                     movie.name,
                     movie.description,
                     movie.rate,
                     movie.popularity,
                     movie.releaseDate
                 )
             )
         }
     }
 }