package farees.hussain.bunkmanager.repositories

import androidx.lifecycle.LiveData
import farees.hussain.bunkmanager.db.database.SubjectDao
import farees.hussain.bunkmanager.db.model.Subject
import javax.inject.Inject

class DefaultSubjectRepository @Inject constructor(
    private val subjectDao: SubjectDao
) : SubjectRepository{
    override suspend fun insertSubjectItem(subjectItem: Subject) {
        subjectDao.insertSubjectItem(subjectItem)
    }

    override suspend fun deleteSubjectItem(subjectItem: Subject) {
        subjectDao.deleteSubjectItem(subjectItem)
    }

    override suspend fun updateSubjectItem(subjectItem: Subject) {
        subjectDao.updateSubjectItem(subjectItem)
    }

    override fun observeAllSubjectItems() = subjectDao.observeAllSubjectItems()
    override fun observeTotalMustAttend() = subjectDao.observeTotalMustAttendClasses()
    override fun observeTotalCanBunk() = subjectDao.observeTotalCanBunkClasses()
    override fun observeTotalClassesAttended() = subjectDao.observeTotalClassesAttended()
    override fun observeTotalClassesBunked() = subjectDao.observeTotalClassesBunked()
}