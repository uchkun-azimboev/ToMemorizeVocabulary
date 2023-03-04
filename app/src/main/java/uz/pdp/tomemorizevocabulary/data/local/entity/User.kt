package uz.pdp.tomemorizevocabulary.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    var name: String,
    @PrimaryKey(autoGenerate = false) var username: String,
    @ColumnInfo(name = "all_categories") var allCategories: Int = 0,
    var completed: Int = 0
)
