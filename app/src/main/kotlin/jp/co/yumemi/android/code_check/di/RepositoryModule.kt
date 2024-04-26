package jp.co.yumemi.android.code_check.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.data.repository.GithubRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideGithubRepository(
        @ApplicationContext context: Context,
    ): GithubRepository = GithubRepository(context)
}
