package uz.pdp.tomemorizevocabulary.model

data class Word(
    var phrase: String,
    var meaning: String,
    var image: Int? = null,
    var successCount: Int,
    var allCount: Int,
    var color: Int
)
