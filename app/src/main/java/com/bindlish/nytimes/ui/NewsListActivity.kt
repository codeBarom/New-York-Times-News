package com.bindlish.nytimes.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bindlish.nytimes.R
import com.bindlish.nytimes.data.NewsData
import com.bindlish.nytimes.data.network.Status

import com.bindlish.nytimes.ui.adapter.NewsListAdapter
import com.bindlish.nytimes.viewmodel.NewsViewModel
import com.bindlish.nytimes.viewmodel.ViewModelFactory
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_news_list.*
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.news_list.*
import javax.inject.Inject

/**
 * An activity representing a list of news.
 */
class NewsListActivity : AppCompatActivity() {

    private var twoPane: Boolean = false
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: NewsViewModel
    private lateinit var listAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)
        AndroidInjection.inject(this)

        window.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
        // casting custom toolbar and set it as action bar
        setSupportActionBar(toolbar)
        // configuring behaviour of toolbar
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewsViewModel::class.java)

        if (item_detail_container != null) {
            twoPane = true
        }
        initialiseViews()
    }

    private fun initialiseViews() {
        news_list?.apply {
            listAdapter = NewsListAdapter(this@NewsListActivity, twoPane)
            adapter = listAdapter
            setHasFixedSize(true)
        }
        viewModel.apply {
            fetchUpdatedData(false)
            // observe live data from view model
            getNewsLiveData().observe(this@NewsListActivity, Observer {
                when (it.status) {
                    Status.SUCCESS -> displayReposData(it.data)
                    Status.LOADING -> displayLoadingView()
                    Status.ERROR -> displayErrorLayout()
                }
            })
        }
        // handling click for retry, hit api and show shimmer
        retry_button.setOnClickListener {
            onRefresh()
        }
    }

    private fun onRefresh() {
        viewModel.fetchUpdatedData(true)
    }

    // method to display data into list after fetching from repository
    private fun displayReposData(news: List<NewsData>?) {
        news?.let {
            listAdapter.setNewsData(news)
            error_layout.visibility = View.GONE
            hideShimmer()
            news_list?.scheduleLayoutAnimation()
        }
    }

    // stop shimmer effect in onPause
    override fun onPause() {
        hideShimmer()
        super.onPause()
    }

    // method to show loading view
    private fun displayLoadingView() {
        if (listAdapter.itemCount == 0) {
            showShimmer()
        }
        error_layout.visibility = View.GONE
    }

    // method to show error layout
    private fun displayErrorLayout() {
        if (listAdapter.itemCount == 0) {
            error_layout.visibility = View.VISIBLE
        } else {
            error_layout.visibility = View.GONE
        }
        hideShimmer()
    }

    // method to show and start shimmer effect
    private fun showShimmer() {
        shimmer_view_container.visibility = View.VISIBLE
        shimmer_view_container.startShimmer()
    }

    // method to hide and stop shimmer affect
    private fun hideShimmer() {
        shimmer_view_container.stopShimmer()
        shimmer_view_container.visibility = View.GONE
    }
}
