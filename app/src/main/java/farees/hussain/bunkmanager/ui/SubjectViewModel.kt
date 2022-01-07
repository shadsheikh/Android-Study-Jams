package farees.hussain.bunkmanager.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import farees.hussain.bunkmanager.db.model.Subject
import farees.hussain.bunkmanager.other.Event
import farees.hussain.bunkmanager.other.Resource
import farees.hussain.bunkmanager.repositories.SubjectRepository
import kotlinx.coroutines.launch

class SubjectViewModel @ViewModelInject constructor(
    private val repository: SubjectRepository
) : ViewModel(){
    val subjectItems = repository.observeAllSubjectItems()
    val totalMustAttend = repository.observeTotalMustAttend()
    val totalCanBunk = repository.observeTotalCanBunk()
    val totalClassesAttended = repository.observeTotalClassesAttended()
    val totalClassesBunked = repository.observeTotalClassesBunked()

    private val _insertSubjectItemStatus = MutableLiveData<Event<Resource<Subject>>>()
    val insertSubjectItemStatus : LiveData<Event<Resource<Subject>>> = _insertSubjectItemStatus

    fun insertSubjectItem(subject: Subject) = viewModelScope.launch {
        repository.insertSubjectItem(subject)
    }
    fun insertShoppingItem(subjectName: String,requiredPercentage:String, classesAttended:String, totalClasses:String){
        var requiredPercentage = requiredPercentage
        var classesAttended = classesAttended
        var totalClasses = totalClasses
        if(subjectName.isEmpty()){
            _insertSubjectItemStatus.postValue(Event(Resource.error("Subject Name Cant' be Empty",null)))
            return
        }
        if(requiredPercentage.isEmpty()){
            requiredPercentage = "75"
        }
        if(classesAttended.isEmpty()) classesAttended = "0"
        if(totalClasses.isEmpty()) totalClasses = "0"
        if(classesAttended.toInt() > totalClasses.toInt()){
            _insertSubjectItemStatus.postValue(Event(Resource.error("Classes Attended Must be Less Than Total Classes",null)))
            return
        }

        val subjectItem = getCompleteSubjectItem(subjectName,requiredPercentage,classesAttended,totalClasses)
        _insertSubjectItemStatus.postValue(Event(Resource.success(subjectItem)))
    }

    fun getCompleteSubjectItem(
        subjectName: String,
        requiredPercentage: String,
        classesAttended: String,
        totalClasses: String
    ): Subject {
        var status: String
        var currentAttendance : String
        var percentageAttendance : Double = if(totalClasses == "0") 0.0 else Math.round((classesAttended.toInt().toDouble()*100/totalClasses.toInt()).toDouble() * 10.0)/10.0
        var noOfClassesToAttend = 0
        var noOfClassesCanBeBunked = 0
        var requiredPercentage = requiredPercentage.toInt()
        if(totalClasses=="0"){
            status = "Attendance Not Yet Started"
            currentAttendance = "Attendance Not Yet Started"
        }
        else{
            currentAttendance = "Current Attendance : $classesAttended/$totalClasses"
            if(percentageAttendance<requiredPercentage){
                noOfClassesToAttend = 3 * totalClasses.toInt() - 4 * classesAttended.toInt()
                if (noOfClassesToAttend < 0) noOfClassesToAttend++
                var classesMustAttend = noOfClassesToAttend
                status = "To get More Than ${requiredPercentage}% Attend $noOfClassesToAttend classes"
            } else {
                var a = classesAttended.toInt()
                var t = totalClasses.toInt()
                while(a*100/t >= requiredPercentage.toDouble()){
                    noOfClassesCanBeBunked++
                    t++
                }
                if(a*100/t<75)noOfClassesCanBeBunked--
                status = if(noOfClassesCanBeBunked>0) "You Bunk $noOfClassesCanBeBunked classes" else "You Can't Bunk any Class Now"
            }
        }
        return Subject(
            subjectName = subjectName,
            requiredPercentageAttendance = requiredPercentage.toInt(),
            classesAttended = classesAttended.toInt(),
            totalClasses = totalClasses.toInt(),
            status = status,
            currentAttendance = currentAttendance,
            classesCanBeBunked = noOfClassesCanBeBunked,
            classesMustAttend = noOfClassesToAttend,
            attendanceCheckedToday = false,
            percentageAttendance = percentageAttendance
        )
    }

    fun updateSubjectItem(subject: Subject) = viewModelScope.launch{
        repository.updateSubjectItem(subject)
    }

}