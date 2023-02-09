package uz.pdp.tomemorizevocabulary.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import uz.pdp.tomemorizevocabulary.model.photos.ResponsePhotos
import uz.pdp.tomemorizevocabulary.utils.Constants

interface ApiService {

    @GET("search")
    suspend fun getPhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") per_page: Int = 50,
        @Query("query") query: String
    ): Response<ResponsePhotos>

}