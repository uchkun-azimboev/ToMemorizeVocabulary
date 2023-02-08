package uz.pdp.tomemorizevocabulary.model.photos

data class ResponsePhotos(
    val next_page: String? = null,
    val page: Int,
    val per_page: Int,
    val photos: ArrayList<Photo>,
    val prev_page: String? = null,
    val total_results: Int
)