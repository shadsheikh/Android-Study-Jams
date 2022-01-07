package farees.hussain.bunkmanager.utils

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import farees.hussain.bunkmanager.R
import farees.hussain.bunkmanager.db.model.Subject

//todo add binding adapters to the subjectItems
@BindingAdapter("subjectPercentageAttendance")
fun TextView.setAttendancPercentage(item: Subject){
    text = "${item.percentageAttendance}%"
}
@BindingAdapter("subjectName")
fun TextView.setSubjectName(item:Subject){
    text = item.subjectName
}
@BindingAdapter("currentAttendance")
fun TextView.setCurrentAttendance(item: Subject){
    text = item.currentAttendance
    setTextColor(resources.getColor(R.color.canBunk))
}
