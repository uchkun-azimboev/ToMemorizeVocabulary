package uz.pdp.tomemorizevocabulary.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.pdp.tomemorizevocabulary.data.remote.ApiService
import uz.pdp.tomemorizevocabulary.repository.PhotoRepository
import uz.pdp.tomemorizevocabulary.repository.PhotoRepositoryImp
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun providePhotoRepository(apiService: ApiService): PhotoRepository {
        return PhotoRepositoryImp(apiService)
    }

}