package com.example.movies.di

import com.example.movies.data.DataSourceImp
import com.example.movies.domain.DataSource
import com.example.movies.domain.Repo
import com.example.movies.domain.RepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityModule {

    @Binds
    abstract fun bindRepoImp(repoImpl: RepoImpl): Repo

    @Binds
    abstract fun bindDatasourceImp(dataSourceImp: DataSourceImp): DataSource
}