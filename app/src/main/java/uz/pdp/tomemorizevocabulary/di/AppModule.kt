package uz.pdp.tomemorizevocabulary.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.pdp.tomemorizevocabulary.data.local.dao.WordDao
import uz.pdp.tomemorizevocabulary.data.local.AppDatabase
import uz.pdp.tomemorizevocabulary.data.local.dao.CategoryDao
import uz.pdp.tomemorizevocabulary.data.local.dao.UserDao
import uz.pdp.tomemorizevocabulary.repository.*
import uz.pdp.tomemorizevocabulary.utils.Constants.LOCAL_SHARED_PREF
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(LOCAL_SHARED_PREF, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideWordDao(appDatabase: AppDatabase): WordDao {
        return appDatabase.wordDao()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(appDatabase: AppDatabase): CategoryDao {
        return appDatabase.categoryDao()
    }

    @Singleton
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Singleton
    @Provides
    fun provideWordRepository(wordDao: WordDao): WordRepository {
        return WordRepositoryImp(wordDao)
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository {
        return CategoryRepositoryImp(categoryDao)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        userDao: UserDao,
        sharedPreferences: SharedPreferences
    ): UserRepository {
        return UserRepositoryImp(userDao, sharedPreferences)
    }

}