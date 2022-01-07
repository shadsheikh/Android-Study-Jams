package farees.hussain.bunkmanager.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import farees.hussain.bunkmanager.MainCoroutineRule
import farees.hussain.bunkmanager.getOrAwaitValueTest
import farees.hussain.bunkmanager.other.Status
import farees.hussain.bunkmanager.repositories.FakeSubjectRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SubjectViewModelTest{
    private lateinit var viewModel: SubjectViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup(){
        viewModel = SubjectViewModel(FakeSubjectRepository())
    }

    //! -> tests for insertShoppingItem

    @Test
    fun `inserting subject item with empty subject name returns error`(){
        viewModel.insertShoppingItem("","60","3","4")
        val value = viewModel.insertSubjectItemStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }
    @Test
    fun `inserting subject item with empty required percentage returns success with 75 percentage`(){
        viewModel.insertShoppingItem("name","","3","4")
        val value = viewModel.insertSubjectItemStatus.getOrAwaitValueTest()
        assertThat(value.peekContent().data?.percentageAttendance).isEqualTo(75.0)
    }
    @Test
    fun `inserting subject item with empty classes attended and total classe returns success with sum of classes attended and total clasess 0`(){
        viewModel.insertShoppingItem("name","75","","")
        val value = viewModel.insertSubjectItemStatus.getOrAwaitValueTest()
        assertThat(value.peekContent().data?.totalClasses!! + value.peekContent().data?.classesAttended!!).isEqualTo(0)
    }
    @Test
    fun `inserting subject item with totalClasses less than classesAttended returns error`(){
        viewModel.insertShoppingItem("name","54","5","4")
        val value = viewModel.insertSubjectItemStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }
    //Todo ->>>> to add time for alarm notification and a check box to set if the user needs the alarm notification

    //! -> tests for getCompleteSubjectItem
    @Test
    fun `testing the percentage attendance`(){
        val subject = viewModel.getCompleteSubjectItem(
            "subject Name",
            "75",
            "3",
            "4"
        )
        assertThat(subject.percentageAttendance).isEqualTo(75.0)
    }
    @Test
    fun `testing the can Bunk classes if percentage is required Percentage`(){
        val subject = viewModel.getCompleteSubjectItem(
            "subject Name",
            "75",
            "3",
            "4"
        )
        assertThat(subject.classesCanBeBunked).isEqualTo(0)
    }
    @Test
    fun `testing the can Bunk classes  if percentage less than required Percentage`(){
        val subject = viewModel.getCompleteSubjectItem(
            "subject Name",
            "75",
            "3",
            "6"
        )
        assertThat(subject.classesCanBeBunked).isEqualTo(0)
    }
    @Test
    fun `testing the can Bunk classes  if percentage slightly greater than required Percentage`(){
        val subject = viewModel.getCompleteSubjectItem(
            "subject Name",
            "75",
            "92",
            "122"
        )
        assertThat(subject.classesCanBeBunked).isEqualTo(0)
    }
   @Test
    fun `testing the can Bunk classes  if percentage greater than required Percentage`(){
        val subject = viewModel.getCompleteSubjectItem(
            "subject Name",
            "75",
            "93",
            "123"
        )
        assertThat(subject.classesCanBeBunked).isEqualTo(1)
    }

    @Test
    fun `testing the must attend classes if percentage is required Percentage`(){
        val subject = viewModel.getCompleteSubjectItem(
            "subject Name",
            "75",
            "3",
            "4"
        )
        assertThat(subject.classesMustAttend).isEqualTo(0)
    }
    @Test
    fun `testing the must attend classes if percentage is less than required Percentage`(){
        val subject = viewModel.getCompleteSubjectItem(
            "subject Name",
            "75",
            "93",
            "125"
        )
        assertThat(subject.classesMustAttend).isEqualTo(3)
    }
    @Test
    fun `testing the must attend classes if percentage is greater than required Percentage`(){
        val subject = viewModel.getCompleteSubjectItem(
            "subject Name",
            "75",
            "19",
            "24"
        )
        assertThat(subject.classesMustAttend).isEqualTo(0)
    }


}