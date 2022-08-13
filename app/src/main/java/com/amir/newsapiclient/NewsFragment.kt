package com.amir.newsapiclient

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amir.newsapiclient.data.util.Resource
import com.amir.newsapiclient.databinding.FragmentNewsBinding
import com.amir.newsapiclient.presentation.adapter.NewsAdapter
import com.amir.newsapiclient.presentation.viewModel.NewsViewModel


class NewsFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private var country = "us"
    private var page = 1
    private var isScrolling = false  //1

    //to check the loading
    private var isLoading = false  //3

    //to check the last page
    private var isLastPage = false  //6
    private var pages = 0  //6
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        //write codes to share this injected dependency from the main activity.
        newsAdapter = (activity as MainActivity).newsAdapter
        //1
        // invoke news adapters setOnItemClickListener we just created.
        newsAdapter.setOnItemClickListener {
            //We are going to pass that article instance to the info fragment using navigations arguments.
            //To do that we have to make the Article instance Serializable.
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            //codes to navigate from news fragment to the info fragment using navController.
            findNavController().navigate(
                R.id.action_newsFragment_to_infoFragment,
                //In order to receive this, we need to add and argument to the info fragment
                bundle
            )

        }
        initRecyclerView()
        viewNewsList()
    }

    //2
    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            //in this method we need to write codes to Set isScrolling as true if the user scrolling the list.
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            //we need to get instance of layoutManager
            val layoutManager = binding.rvNews.layoutManager as LinearLayoutManager
            /* Using this layout manager instance, we are going to get 3 properties of the current recyclerview.
             Size of the current list.
             Visible items count Starting
             Position of the visible items.*/
            val sizeOfCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()
            //8
            /*The current list has to reach to the last item before we do pagination.
            //We can check that using the topPosition, visibleItems and the sizeOfTheCurrentList.*/
            val hasReachedToEnd = topPosition + visibleItems >= sizeOfCurrentList
            //we can write codes to decide pagination.
            //If data still loading, we should not paginate.
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling
            if (shouldPaginate) {
                page++
                /*
                When pagination happens. Page number should increase by 1.
                We should invoke the getNewsHeadLines function of the view model using new page number.
                We should also set the isScrolling as false.
                 */
                viewModel.getNewsHeadLines(country, page)
                isScrolling = false
            }
        }
    }

    private fun initRecyclerView() {
        //  newsAdapter = NewsAdapter()
//        //instead we can use kotlin apply function
//        binding.rvNews.adapter = newsAdapter
//        binding.rvNews.layoutManager= LinearLayoutManager(activity)
        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewsFragment.onScrollListener)
        }
    }

    private fun showProgressBar() {
        //When data is loading, we show the progress bar. So we can use this function to set it as true.
        isLoading = true  //4
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false  //5
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun viewNewsList() {

        viewModel.getNewsHeadLines(country, page)
        viewModel.newsHeadlines.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {

                    hideProgressBar()
                    response.data?.let {
                        Log.i("MYTAG", "came here ${it.articles.toList().size}")
                        newsAdapter.differ.submitList(it.articles.toList())
                        //7
                        if (it.totalResults % 20 == 0) {
                            //st after submitting list to the adapter we can write codes to check if the current  page is the last page.
                            //20 is the default page size of the api.
                            pages = it.totalResults / 20
                        } else {
                            //If current page number equals to the number of pages that means list is in the last page.
                            pages = it.totalResults / 20 + 1
                        }
                        isLastPage = page == pages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred : $it", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }

            }
        })
    }

}