package farees.hussain.bunkmanager.repositories

import androidx.lifecycle.LiveData
import farees.hussain.bunkmanager.db.model.Subject

interface SubjectRepository {
    suspend fun insertSubjectItem(subjectItem: Subject)
    suspend fun deleteSubjectItem(subjectItem: Subject)
    suspend fun updateSubjectItem(subjectItem: Subject)
    fun observeAllSubjectItems() : LiveData<List<Subject>>
    fun observeTotalMustAttend() : LiveData<Int>
    fun observeTotalCanBunk() : LiveData<Int>
    fun observeTotalClassesAttended() : LiveData<Int>
    fun observeTotalClassesBunked() : LiveData<Int>
}