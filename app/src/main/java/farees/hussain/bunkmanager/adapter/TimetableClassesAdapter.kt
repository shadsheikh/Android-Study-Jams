package farees.hussain.bunkmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import farees.hussain.bunkmanager.R
import farees.hussain.bunkmanager.db.model.TimeTableItem
import farees.hussain.bunkmanager.other.Constants
import kotlinx.android.synthetic.main.item_timetable_subjects.view.*

class TimetableClassesAdapter (
    val classes : List<TimeTableItem>,
    day: Constants.timeTableDay
) : RecyclerView.Adapter<TimetableClassesAdapter.TimeTableViewHolder>() {
    inner class TimeTableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    val selectedDayClassses = classes.filter { (timeTableDay) -> timeTableDay == day }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TimeTableViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_timetable_subjects,parent,false))

    override fun onBindViewHolder(holder: TimeTableViewHolder, position: Int) {
        val item = selectedDayClassses[position]
        holder.itemView.apply {
            tvSubjectTime.text = item.subjectName
            tvAlaramTime.text = "${item.classStartTime} ${item.AMorPM}"
        }
    }

    override fun getItemCount() = selectedDayClassses.size
}