package com.amir.newsapiclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.amir.newsapiclient.databinding.ActivityMainBinding
import com.amir.newsapiclient.presentation.adapter.NewsAdapter
import com.amir.newsapiclient.presentation.viewModel.NewsViewModel
import com.amir.newsapiclient.presentation.viewModel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//To construct this view model, we need a view model factory. We are going to get it using dependency injection.
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //for this project we use one activity and multiple fragment
    //So, we will construct and instance of view model inside this main activity and share it among fragments. Let’s define a view model here.
    @Inject
    lateinit var factory: NewsViewModelFactory
    //go to the MainActivity and create an object reference variable for the NewsAdapter .
    @Inject
    lateinit var newsAdapter: NewsAdapter
    lateinit var viewModel: NewsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bnvNews.setupWithNavController(navController)

        viewModel = ViewModelProvider(this,factory).get(NewsViewModel::class.java)
    }
}