package farees.hussain.bunkmanager.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import farees.hussain.bunkmanager.db.model.Subject

class FakeSubjectRepository : SubjectRepository {
    private val subjectItems = mutableListOf<Subject>()

    private val observableSubjectItems = MutableLiveData<List<Subject>>()

    private val observableMustAttendClasses = MutableLiveData<Int>()
    private val observableCanBunkClasses = MutableLiveData<Int>()
    private val observableClassesAttended = MutableLiveData<Int>()
    private val observableClassesBunked = MutableLiveData<Int>()

    private fun refreshLiveData(){
        observableSubjectItems.postValue(subjectItems)
        observableMustAttendClasses.postValue(getMustAttendClasses())
        observableCanBunkClasses.postValue(getCanBunkClasses())
        observableClassesAttended.postValue(getTotalAttendedClasses())
        observableClassesBunked.postValue(getTotalBunkedClasses())
    }

    private fun getMustAttendClasses() = subjectItems.sumBy { it.classesMustAttend!! }
    private fun getCanBunkClasses() = subjectItems.sumBy { it.classesCanBeBunked!! }
    private fun getTotalAttendedClasses() = subjectItems.sumBy { it.classesAttended }
    private fun getTotalBunkedClasses() = subjectItems.sumBy { (it.totalClasses - it.classesAttended) }

    override suspend fun insertSubjectItem(subjectItem: Subject) {
        subjectItems.add(subjectItem)
        refreshLiveData()
    }

    override suspend fun deleteSubjectItem(subjectItem: Subject) {
        subjectItems.remove(subjectItem)
        refreshLiveData()
    }

    override fun observeAllSubjectItems() = observableSubjectItems
    override fun observeTotalMustAttend() = observableMustAttendClasses
    override fun observeTotalCanBunk() = observableCanBunkClasses
    override fun observeTotalClassesAttended() = observableClassesAttended
    override fun observeTotalClassesBunked() = observableClassesBunked

}