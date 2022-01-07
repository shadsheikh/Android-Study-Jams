package farees.hussain.bunkmanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import farees.hussain.bunkmanager.R
import farees.hussain.bunkmanager.db.model.TimeTableItem
import farees.hussain.bunkmanager.other.Constants
import kotlinx.android.synthetic.main.item_timetable_pager.view.*

class TimetablePagerAdapter (val items: List<Constants.timeTableDay>, val TimetableClasses : List<TimeTableItem>) : RecyclerView.Adapter<TimetablePagerAdapter.PagerViewHolder>(){
    inner class PagerViewHolder(itemview:View):RecyclerView.ViewHolder(itemview)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_timetable_pager, parent, false))

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int){
        holder.itemView.apply {
            rvTimetableSubjects.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
            rvTimetableSubjects.adapter = TimetableClassesAdapter(TimetableClasses,items[position])
        }
    }

    override fun getItemCount() = items.size
}