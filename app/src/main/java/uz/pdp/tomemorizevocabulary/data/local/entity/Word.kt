package uz.pdp.tomemorizevocabulary.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word")
data class Word(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var phrase: String,
    var meaning: String,
    var image: String? = null,
    @ColumnInfo(name = "success_count") var successCount: Int = 0,
    @ColumnInfo(name = "all_count") var allCount: Int = 0,
    var part: String? = null,
    @ColumnInfo(name = "category_title")
    var categoryTitle: String
)