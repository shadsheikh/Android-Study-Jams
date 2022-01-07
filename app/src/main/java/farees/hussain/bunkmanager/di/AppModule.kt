package farees.hussain.bunkmanager.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import farees.hussain.bunkmanager.db.database.SubjectDao
import farees.hussain.bunkmanager.db.database.SubjectItemsDatabase
import farees.hussain.bunkmanager.other.Constants.DATABASE_NAME
import farees.hussain.bunkmanager.repositories.DefaultSubjectRepository
import farees.hussain.bunkmanager.repositories.SubjectRepository
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDefaultSubjectRepository(
        dao : SubjectDao
    ) = DefaultSubjectRepository(dao) as SubjectRepository

    @Singleton
    @Provides
    fun provideSubjectItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context,SubjectItemsDatabase::class.java,DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideSubjectDao(
        database : SubjectItemsDatabase
    ) = database.subjectDao()
}