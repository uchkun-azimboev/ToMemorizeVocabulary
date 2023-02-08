package uz.pdp.tomemorizevocabulary.repository

import retrofit2.Response
import retrofit2.http.Query
import uz.pdp.tomemorizevocabulary.model.photos.ResponsePhotos
import uz.pdp.tomemorizevocabulary.utils.Resource

interface PhotoRepository {
    suspend fun getPhotos(
        page: Int,
        query: String
    ): Response<ResponsePhotos>
}