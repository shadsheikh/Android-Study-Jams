package farees.hussain.bunkmanager.db.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import farees.hussain.bunkmanager.db.model.Subject
import farees.hussain.bunkmanager.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/*for runBlockingTest*/
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class SubjectDaoTest {
    //! error: This job has not completed yet
    /* this is for the above error */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: SubjectItemsDatabase
    private lateinit var dao: SubjectDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SubjectItemsDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.subjectDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertSubjectItem() = runBlockingTest {
        val subjectItem = Subject("physics",0,"0","",0,0,0,0,false,id=1)
        dao.insertSubjectItem(subjectItem)
        val allShoppingItems = dao.observeAllSubjectItems().getOrAwaitValue()
        assertThat(allShoppingItems).contains(subjectItem)
    }
    @Test
    fun deleteSubjectItem() = runBlockingTest {
        val subjectItem = Subject("physics",0,"0","",0,0,0,0,false,id=1)
        dao.insertSubjectItem(subjectItem)
        dao.deleteSubjectItem(subjectItem)
        val allShoppingItems = dao.observeAllSubjectItems().getOrAwaitValue()
        assertThat(allShoppingItems).doesNotContain(subjectItem)
    }

    @Test
    fun observeTotalMustAttendClasses() = runBlockingTest {
        val subjectItem1 = Subject("math",0,"0","",0,0,0,0,false,id = 1)
        val subjectItem2 = Subject("math",0,"0","",3,4,0,5,false,id = 2)
        val subjectItem3 = Subject("math",0,"0","",3,4,0,10,false,id = 3)
        dao.insertSubjectItem(subjectItem1)
        dao.insertSubjectItem(subjectItem2)
        dao.insertSubjectItem(subjectItem3)
        val totalMustAttend = dao.observeTotalMustAttendClasses().getOrAwaitValue()
        assertThat(totalMustAttend).isEqualTo(15)
    }
    @Test
    fun observeTotalCanBunkClasses() = runBlockingTest {
        val subjectItem1 = Subject("math",0,"0","",0,0,0,0,false,id = 1)
        val subjectItem2 = Subject("math",0,"0","",3,4,5,5,false,id = 2)
        val subjectItem3 = Subject("math",0,"0","",3,4,10,10,false,id = 3)
        dao.insertSubjectItem(subjectItem1)
        dao.insertSubjectItem(subjectItem2)
        dao.insertSubjectItem(subjectItem3)
        val totalMustAttend = dao.observeTotalCanBunkClasses().getOrAwaitValue()
        assertThat(totalMustAttend).isEqualTo(15)
    }
    @Test
    fun observeTotalBunkedClasses() = runBlockingTest {
        val subjectItem1 = Subject("math",0,"0","",16,20,0,0,false,id = 1)
        val subjectItem2 = Subject("math",0,"0","",3,4,5,5,false,id = 2)
        val subjectItem3 = Subject("math",0,"0","",3,4,10,10,false,id = 3)
        dao.insertSubjectItem(subjectItem1)
        dao.insertSubjectItem(subjectItem2)
        dao.insertSubjectItem(subjectItem3)
        val totalMustAttend = dao.observeTotalClassesBunked().getOrAwaitValue()
        assertThat(totalMustAttend).isEqualTo(6)
    }
    @Test
    fun observeTotalAttendedClasses() = runBlockingTest {
        val subjectItem1 = Subject("math",0,"0","",16,20,0,0,false,id = 1)
        val subjectItem2 = Subject("math",0,"0","",3,4,5,5,false,id = 2)
        val subjectItem3 = Subject("math",0,"0","",3,4,10,10,false,id = 3)
        dao.insertSubjectItem(subjectItem1)
        dao.insertSubjectItem(subjectItem2)
        dao.insertSubjectItem(subjectItem3)
        val totalMustAttend = dao.observeTotalClassesAttended().getOrAwaitValue()
        assertThat(totalMustAttend).isEqualTo(22)
    }

    @Test
    fun updateSubjectItem() = runBlockingTest {
        val subjectItem = Subject("math",0,"0","",16,20,0,0,false,id = 1)
        dao.insertSubjectItem(subjectItem)
        val subjectItem1 = Subject("math",0,"0","",17,21,0,0,false,id = 1)
        dao.updateSubjectItem(subjectItem1)
        val allShoppingItems = dao.observeAllSubjectItems().getOrAwaitValue()
        assertThat(allShoppingItems).contains(subjectItem1)
    }
}