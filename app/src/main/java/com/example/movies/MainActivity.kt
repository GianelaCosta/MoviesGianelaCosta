package com.example.movies

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.movies.data.DataSource
import com.example.movies.domain.RepoImpl
import com.example.movies.ui.viewModel.MainViewModel
import com.example.movies.ui.viewModel.VMFactory
import com.facebook.stetho.Stetho

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
        Stetho.initializeWithDefaults(this)
        val online = verifyAvailableNetwork(this)
        Toast.makeText(this, online.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }


    fun verifyAvailableNetwork(activity: AppCompatActivity):Boolean{
        val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }
}