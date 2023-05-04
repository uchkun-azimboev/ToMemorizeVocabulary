package uz.pdp.tomemorizevocabulary.model

import java.io.Serializable

data class GameInfo(
    var game: String,
    var gameType: String,
    var categoryId: Int = 0,
    var wordCount: Int = 0
) : Serializable
