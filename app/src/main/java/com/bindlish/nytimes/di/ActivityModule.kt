package com.bindlish.nytimes.di

import com.bindlish.nytimes.ui.NewsDetailActivity
import com.bindlish.nytimes.ui.NewsDetailFragment
import com.bindlish.nytimes.ui.NewsListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeNewsListActivity(): NewsListActivity

    @ContributesAndroidInjector
    internal abstract fun contributeNewsDetailActivity(): NewsDetailActivity

    @ContributesAndroidInjector
    internal abstract fun contributeNewsDetailFragment(): NewsDetailFragment
}