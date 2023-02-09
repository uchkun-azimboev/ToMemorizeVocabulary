package uz.pdp.tomemorizevocabulary.model

data class Category(
    var title: String,
    var description: String,
    var wordCount: Int,
    var createDate: String,
    var color: Int
)