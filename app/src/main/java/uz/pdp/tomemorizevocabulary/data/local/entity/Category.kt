package uz.pdp.tomemorizevocabulary.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String,
    var description: String,
    @ColumnInfo(name = "word_count")
    var wordCount: Int = 0,
    @ColumnInfo(name = "create_date")
    var createDate: String,
    var color: Int
) : Serializable