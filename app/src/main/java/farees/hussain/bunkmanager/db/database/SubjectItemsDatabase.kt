package farees.hussain.bunkmanager.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import farees.hussain.bunkmanager.db.model.Subject

@Database(
    entities = [Subject::class],
    version = 1
)
abstract class SubjectItemsDatabase :RoomDatabase(){
    abstract fun subjectDao(): SubjectDao
}