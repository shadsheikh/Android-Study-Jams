package farees.hussain.bunkmanager.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import farees.hussain.bunkmanager.other.Constants
import farees.hussain.bunkmanager.other.Constants.TIMETABLE_TABLE_NAME
import java.sql.Time
import java.time.DayOfWeek

@Entity(tableName = TIMETABLE_TABLE_NAME)
data class TimeTableItem(
    val day: Constants.timeTableDay,
    val subjectName: String,
    val classStartTime: Time,
    val AMorPM: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null
)