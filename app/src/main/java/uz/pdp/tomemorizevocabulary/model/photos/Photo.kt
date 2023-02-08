package uz.pdp.tomemorizevocabulary.model.photos

data class Photo(
    val alt: String,
    val avg_color: String,
    val height: Int,
    val id: Int,
    val liked: Boolean,
    val photographer: String,
    val photographer_id: Int,
    val photographer_url: String,
    val src: PhotoSrc,
    val url: String,
    val width: Int
)