package uz.pdp.tomemorizevocabulary.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.pdp.tomemorizevocabulary.data.local.dao.CategoryDao
import uz.pdp.tomemorizevocabulary.data.local.dao.UserDao
import uz.pdp.tomemorizevocabulary.data.local.dao.WordDao
import uz.pdp.tomemorizevocabulary.data.local.entity.Category
import uz.pdp.tomemorizevocabulary.data.local.entity.Word
import uz.pdp.tomemorizevocabulary.data.local.entity.User
import uz.pdp.tomemorizevocabulary.utils.Constants

@Database(entities = [Word::class, Category::class, User::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao
    abstract fun categoryDao(): CategoryDao
    abstract fun userDao(): UserDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {

            if (instance == null)
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    Constants.WORD_DATABASE
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()

            return instance!!
        }
    }

}