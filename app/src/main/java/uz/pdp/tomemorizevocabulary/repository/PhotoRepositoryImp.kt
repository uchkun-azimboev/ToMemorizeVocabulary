package uz.pdp.tomemorizevocabulary.repository

import retrofit2.Response
import uz.pdp.tomemorizevocabulary.data.remote.ApiService
import uz.pdp.tomemorizevocabulary.model.photos.ResponsePhotos

class PhotoRepositoryImp(private val apiService: ApiService) : PhotoRepository {

    override suspend fun getPhotos(page: Int, query: String): Response<ResponsePhotos> {
        return apiService.getPhotos(page = page, query = query)
    }

}