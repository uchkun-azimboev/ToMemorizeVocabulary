package uz.pdp.tomemorizevocabulary.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey
    var title: String,
    var description: String,
    @ColumnInfo(name = "word_count")
    var wordCount: Int = 0,
    @ColumnInfo(name = "create_date")
    var createDate: String,
    var color: Int
)