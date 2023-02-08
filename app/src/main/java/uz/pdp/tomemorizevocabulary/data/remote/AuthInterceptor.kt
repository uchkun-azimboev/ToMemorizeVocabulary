package uz.pdp.tomemorizevocabulary.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import uz.pdp.tomemorizevocabulary.utils.Constants

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader(Constants.AUTHORIZATION, Constants.API_KEY)
        return chain.proceed(requestBuilder.build())
    }
}