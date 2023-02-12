package uz.pdp.tomemorizevocabulary.model.photos

data class ThreePhoto(
    val photo1: Photo,
    val photo2: Photo? = null,
    val photo3: Photo? = null
)
