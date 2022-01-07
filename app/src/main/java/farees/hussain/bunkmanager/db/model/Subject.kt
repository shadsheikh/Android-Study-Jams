package farees.hussain.bunkmanager.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import farees.hussain.bunkmanager.other.Constants.SUBJECT_TABLE_NAME

@Entity(tableName = SUBJECT_TABLE_NAME)
data class Subject(
    var subjectName:String,
    var requiredPercentageAttendance:Int=75,
    var currentAttendance: String,
    var status: String,
    var classesAttended:Int,
    var totalClasses: Int,
    var classesCanBeBunked:Int?=null,
    var classesMustAttend:Int?=null,
    var attendanceCheckedToday:Boolean ?=false,
    var percentageAttendance:Double = if(totalClasses == 0)0.0 else Math.round((classesAttended.toDouble()*100/totalClasses).toDouble() * 10.0)/10.0,

    @PrimaryKey(autoGenerate = true)
    val id: Long?=null
)